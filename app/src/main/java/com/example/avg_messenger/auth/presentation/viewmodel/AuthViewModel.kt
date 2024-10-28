package com.example.avg_messenger.auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avg_messenger.auth.data.model.User
import com.example.avg_messenger.auth.domain.model.AuthState
import com.example.avg_messenger.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        Log.d( "MyTag", "$email, $password")
        viewModelScope.launch {
            // Устанавливаем состояние загрузки
            _authState.value = AuthState.Loading

            // Обрабатываем аутентификацию
            try {
                val response = loginUseCase.execute(email, password)
                // Успешная аутентификация
                _authState.value = AuthState.Success("fdsfsdfs")
            } catch (e: Exception) {
                // Обработка ошибок
                _authState.value = AuthState.Error(e.localizedMessage ?: "Ошибка авторизации")
            }
        }
    }
}