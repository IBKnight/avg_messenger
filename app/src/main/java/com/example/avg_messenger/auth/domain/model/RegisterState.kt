package com.example.avg_messenger.auth.domain.model

sealed class RegisterState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}