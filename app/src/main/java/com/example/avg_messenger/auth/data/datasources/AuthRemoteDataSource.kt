package com.example.avg_messenger.auth.data.datasources

import com.example.avg_messenger.auth.data.model.AuthResponseModel
import com.example.avg_messenger.auth.data.model.LoginRequest
import com.example.avg_messenger.auth.data.model.RefreshRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRemoteDataSource {
    @POST("user/sign-in")
    suspend fun login(@Body request: LoginRequest): AuthResponseModel

    @POST("user/sign-up")
    suspend fun register(@Body request: LoginRequest)

    @POST("/user/refresh/0")
    suspend fun refresh(@Body request: RefreshRequest): String
}