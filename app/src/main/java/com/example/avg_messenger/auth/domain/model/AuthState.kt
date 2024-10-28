package com.example.avg_messenger.auth.domain.model

import com.example.avg_messenger.auth.data.model.User

sealed class AuthState {
    data object Idle : AuthState() // Начальное состояние
    data object Loading : AuthState() // Состояние загрузки
    data class Success(val token: String) : AuthState() // Успех с токеном
    data class Error(val message: String) : AuthState() // Ошибка с сообщением
}