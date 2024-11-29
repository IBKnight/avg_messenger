package com.example.avg_messenger.auth.data.repository

import android.util.Log
import com.example.avg_messenger.auth.data.TokenManager
import com.example.avg_messenger.auth.data.datasources.AuthRemoteDataSource
import com.example.avg_messenger.auth.data.model.LoginRequest
import com.example.avg_messenger.auth.data.model.RefreshRequest
import com.example.avg_messenger.auth.domain.repository.AuthRepository
import com.example.avg_messenger.auth.data.model.User
import com.example.avg_messenger.chat_list.data.datasources.ChatListRemoteDataSource
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val chatListRemoteDataSource: ChatListRemoteDataSource,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(login: String, password: String): User {
        val response = authRemoteDataSource.login(LoginRequest(login, password))

        tokenManager.saveTokens(
            userId = response.userId,
            accessToken = response.tokenModel.accessToken,
            refreshToken = response.tokenModel.refreshToken
        )

        Log.i("AuthRepositoryImpl", "$response")

        return User(id = response.userId, login = login, userName = "")
    }

    override suspend fun register(login: String, password: String) {
        authRemoteDataSource.register(LoginRequest(login, password))
    }

    override suspend fun refreshToken(): Boolean? {
        val refreshToken = tokenManager.getRefreshToken() ?: return null
        val userId = if (tokenManager.getUserId() != -1) tokenManager.getUserId() else return null

        return try {
            val accessToken = authRemoteDataSource.refresh(
                RefreshRequest(
                    refreshToken = refreshToken,
                    userId = userId
                )
            )

            tokenManager.saveTokens(
                accessToken = accessToken.accessToken,
                refreshToken = refreshToken,
                userId = userId
            )
            true
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Error refreshing token: ${e.localizedMessage}")
            null // Возвратите null в случае ошибки
        }
    }


    override suspend fun checkTokenValidity(): Boolean {
        return try {
            tokenManager.getAccessToken() ?: return false

            Log.e("checkTokenValidity", tokenManager.getAccessToken().toString() )

           val a = chatListRemoteDataSource.getAllChats()


            //Log.i("allchats", a.toString() )

            true // Токен действителен
        } catch (e: HttpException) {
            if (e.code() == 401) {
                false // Токен недействителен
            } else {
                throw e // Другие ошибки
            }
        }
    }

    override suspend fun logout() {
        tokenManager.clearTokens()
    }
}
