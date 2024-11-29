package com.example.avg_messenger.auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avg_messenger.auth.data.TokenManager
import com.example.avg_messenger.auth.domain.model.AuthState
import com.example.avg_messenger.auth.domain.model.RegisterState
import com.example.avg_messenger.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _registerState = MutableStateFlow<AuthState>(RegisterState.Idle)
    val registerState: StateFlow<AuthState> = _registerState

    fun resetRegisterState() {
        _registerState.value = RegisterState.Idle
    }

    fun checkTokenAndLogin() {
        viewModelScope.launch {
            val isTokenValid = authRepository.checkTokenValidity()
            if (isTokenValid) {
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("")

            }
        }
    }

    fun register(email: String, password: String, confirmPassword: String) {
        Log.d("Register", "$email, $password")
        viewModelScope.launch {
            if (password != confirmPassword) {
                _registerState.value = RegisterState.Error("Пароли не совпадают")
                return@launch
            }
            // Устанавливаем состояние загрузки
            _registerState.value = RegisterState.Loading

            // Обрабатываем аутентификацию
            try {
                val response = authRepository.register(email, password)
                // Успешная аутентификация
                _registerState.value = RegisterState.Success
            } catch (e: Exception) {
                // Обработка ошибок
                _registerState.value =
                    RegisterState.Error(e.localizedMessage ?: "Ошибка регистрации")
            }
        }
    }

    fun login(email: String, password: String) {
        Log.d("Login", "$email, $password")
        viewModelScope.launch {
            // Устанавливаем состояние загрузки
            _authState.value = AuthState.Loading

            // Обрабатываем аутентификацию
            try {
                val response = authRepository.login(email, password)
                // Успешная аутентификация
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                // Обработка ошибок
                _authState.value = AuthState.Error(e.localizedMessage ?: "Ошибка авторизации")
            }
        }
    }

    fun logout() {
        Log.d("Login", "")
        viewModelScope.launch {
            // Устанавливаем состояние загрузки
            _authState.value = AuthState.Loading

            // Обрабатываем аутентификацию
            try {
                val response = authRepository.logout()

            } catch (e: Exception) {
                // Обработка ошибок
                _authState.value = AuthState.Error(e.localizedMessage ?: "Ошибка авторизации")
            }
        }

    }
}