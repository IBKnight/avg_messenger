package com.example.avg_messenger.auth.domain.repository

import com.example.avg_messenger.auth.data.model.User

interface AuthRepository {
    suspend fun login(login: String, password: String): User
    suspend fun register(login: String, password: String)
    suspend fun refreshToken(): Boolean?
    suspend fun checkTokenValidity(): Boolean
    suspend fun logout(): Unit

}
