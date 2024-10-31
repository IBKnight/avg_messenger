package com.example.avg_messenger.auth.domain.usecase

import com.example.avg_messenger.auth.domain.repository.AuthRepository
import com.example.avg_messenger.auth.data.model.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(email: String, password: String): User {
        return authRepository.login(email, password)
    }

    suspend fun checkTokenValidity(): Boolean {
        return authRepository.checkTokenValidity()
    }
}