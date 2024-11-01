package com.example.avg_messenger.auth.data

import android.util.Log
import com.example.avg_messenger.BuildConfig
import com.example.avg_messenger.auth.data.datasources.AuthRemoteDataSource
import com.example.avg_messenger.auth.data.model.RefreshRequest
import com.google.gson.Gson
import okhttp3.*
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken: String?

        try {
            // Получение accessToken
            accessToken = tokenManager.getAccessToken()

            // Создание нового запроса с заголовком Authorization
            val requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")

            // Выполнение запроса
            var response = chain.proceed(requestBuilder.build())

            // Проверка статуса ответа, если токен истек
            if (response.code() == 401 || response.code() == 500) {
                Log.d("ОШИБКА ТОКЕНА", response.toString())
                // Если токен истек, пробуем обновить его
                val newAccessToken = refreshAccessToken() // Метод для обновления токена
                if (newAccessToken != null) {
                    // Обновляем токен в TokenManager
                    tokenManager.saveTokens(
                        accessToken = newAccessToken,
                        refreshToken = tokenManager.getRefreshToken() ?: "",
                        userId = tokenManager.getUserId()
                    )

                    // Повторяем оригинальный запрос с новым токеном
                    val retryRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()
                    response = chain.proceed(retryRequest)
                }
            }

            return response

        } catch (e: HttpException) {
            Log.e("interceptorError", e.code().toString())
            // В случае ошибки, возвращаем оригинальный ответ или выбрасываем исключение
            throw e
        } catch (e: Exception) {
            Log.e("interceptorError", "Unexpected error: ${e.message}")
            // Здесь также можно вернуть ответ с ошибкой или пробросить исключение
            throw e
        }
    }

    private fun refreshAccessToken(): String? {
        val client = OkHttpClient()
        val refreshRequest = RefreshRequest(
            userId = tokenManager.getUserId(),
            refreshToken = tokenManager.getRefreshToken() ?: ""
        )

        val requestBody = RequestBody.create(
            MediaType.parse("application/json"),
            Gson().toJson(refreshRequest)
        )

        val request = Request.Builder()
            .url("${BuildConfig.BASE_URL}user/refresh/0")
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                // Предположим, что ответ содержит новый accessToken в виде строки
                response.body()?.string() // Или распарсите ответ, если требуется
            } else {
                Log.e("refreshTokenError", "Failed to refresh token: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("refreshTokenError", "Failed to refresh token: ${e.message}")
            null
        }
    }
}
