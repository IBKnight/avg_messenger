package com.example.avg_messenger.auth.domain.model

sealed class AuthState {
    data object Idle : AuthState() // Начальное состояние
    data object Loading : AuthState() // Состояние загрузки
    data object Success : AuthState() // Успех с токеном
    data class Error(val message: String) : AuthState() // Ошибка с сообщением
}