package com.example.avg_messenger.auth.data

import android.util.Log
import com.example.avg_messenger.BuildConfig
import com.example.avg_messenger.auth.data.model.RefreshRequest
import com.example.avg_messenger.auth.data.model.RefreshResponse
import com.google.gson.Gson
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = tokenManager.getAccessToken()
        Log.i("AccessToken", accessToken.toString())

        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")

        var response = chain.proceed(requestBuilder.build())

        if (response.code() == 401 || response.code() == 500) {
            response.close()

            // Попытка обновить токен
            val newAccessToken = refreshAccessToken()

            Log.d("NewAccessToken", "Получен новый токен: $newAccessToken")
            return if (newAccessToken != null && newAccessToken.isNotEmpty()) {

                tokenManager.saveTokens(
                    accessToken = newAccessToken,
                    refreshToken = tokenManager.getRefreshToken() ?: "",
                    userId = tokenManager.getUserId()
                )

                Log.i("retryRequest", "$newAccessToken")


                // Повторяем запрос с новым токеном
                val retryRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()

                Log.i("retryRequest", "$retryRequest ${retryRequest.headers()}")

                response = chain.proceed(retryRequest)



                Log.i("retryRequest", "${response.headers()}")

                response
            } else {
                response
            }
        }

        return response
    }

    private fun refreshAccessToken(): String? {
        val client = OkHttpClient()
        val refreshRequest = RefreshRequest(
            userId = tokenManager.getUserId(), refreshToken = tokenManager.getRefreshToken() ?: ""
        )


        val requestBody = RequestBody.create(
            MediaType.parse("application/json"), Gson().toJson(refreshRequest)
        )

        val request =
            Request.Builder().url("${BuildConfig.BASE_URL}user/refresh").post(requestBody).build()

        return try {
            val response = client.newCall(request).execute()
            val token: String
            if (response.isSuccessful) {

                val responseBody = response.body()?.string()

                token = Gson().fromJson(responseBody, RefreshResponse::class.java).accessToken

                Log.i("NewToken", "$responseBody")


            } else {
                Log.e("refreshTokenError", "Failed to refresh token: ${response.code()}")
                token = ""
            }

            return token
        } catch (e: Exception) {
            Log.e("refreshTokenError", "Failed to refresh token: ${e.message}")
            null
        }
    }
}
