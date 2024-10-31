package com.example.avg_messenger.auth.domain.usecase

import com.example.avg_messenger.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(email: String, password: String) {
        return authRepository.register(email, password)
    }
}