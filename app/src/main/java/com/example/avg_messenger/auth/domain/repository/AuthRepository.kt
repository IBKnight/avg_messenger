package com.example.avg_messenger.auth.domain.repository

import com.example.avg_messenger.auth.data.model.User

interface AuthRepository {
    suspend fun login(login: String, password: String): User
}
