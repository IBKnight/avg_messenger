package com.example.avg_messenger.auth.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.avg_messenger.auth.domain.model.AuthState
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel? = null
) {

    val authState = authViewModel?.authState?.collectAsState() ?: remember { mutableStateOf(AuthState.Idle) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Обработка ошибок
    val errorMessage = when (authState.value) {
        is AuthState.Error -> (authState.value as AuthState.Error).message
        else -> ""
    }

    // Динамическое изменение заголовка экрана
    val title = when (authState.value) {
        is AuthState.Loading -> "Загрузка..."
        else -> "Авторизация"
    }

    // Разметка экрана
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        EmailInput(email) { email = it }
        Spacer(modifier = Modifier.height(8.dp))
        PasswordInput(password) { password = it }

        Spacer(modifier = Modifier.height(16.dp))

        when (authState.value) {
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }
            else -> {
                AuthButton {
                    Log.d("MyTag", "$authViewModel")
                    authViewModel?.login(email, password)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun EmailInput(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordInput(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Пароль") },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun AuthButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Войти")
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}
