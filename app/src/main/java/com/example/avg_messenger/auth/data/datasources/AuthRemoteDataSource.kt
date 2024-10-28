package com.example.avg_messenger.auth.data.datasources

import com.example.avg_messenger.auth.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRemoteDataSource {
    @POST("user/sign-in")
    suspend fun login(@Body request: LoginRequest): User
}

data class LoginRequest(val login: String, val password: String)
