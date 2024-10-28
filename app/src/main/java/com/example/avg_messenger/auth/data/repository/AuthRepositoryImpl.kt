package com.example.avg_messenger.auth.data.repository

import com.example.avg_messenger.auth.data.datasources.AuthRemoteDataSource
import com.example.avg_messenger.auth.data.datasources.LoginRequest
import com.example.avg_messenger.auth.domain.repository.AuthRepository
import com.example.avg_messenger.auth.data.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(login: String, password: String): User {
        authRemoteDataSource.login(LoginRequest(login, password))

        return User("1", "login") // Пример возвращаемого пользователя
    }
}
