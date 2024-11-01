package com.example.avg_messenger.auth.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avg_messenger.auth.domain.model.AuthState
import com.example.avg_messenger.auth.domain.model.RegisterState
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    authViewModel: AuthViewModel? = null
) {
    val registerState =
        authViewModel?.registerState?.collectAsState() ?: remember { mutableStateOf(RegisterState.Idle) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }


    // Обработка ошибок
    val errorMessage = when (registerState.value) {

        is RegisterState.Error -> (registerState.value as RegisterState.Error).message
        else -> ""
    }

    // Динамическое изменение заголовка экрана
    val title = when (registerState.value) {
        is RegisterState.Loading -> "Загрузка..."
        else -> "Регистрация"
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
        Spacer(modifier = Modifier.height(8.dp))
        PasswordInput(confirmPassword) { confirmPassword = it }

        Spacer(modifier = Modifier.height(16.dp))

        when (registerState.value) {
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }

            else -> {
                RegisterButton {
                    authViewModel?.register(email, password, confirmPassword)
                    Log.i("MyTag", registerState.value.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController?.navigate("auth") }) {
            Text(text = "Войти")
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

    SnackbarHost(hostState = snackbarHostState)

    LaunchedEffect(registerState.value) {
        if (registerState.value is RegisterState.Success) {
            snackbarHostState.showSnackbar(
                message = "Аккаунт успешно создан!",
                duration = SnackbarDuration.Short
            )
            navController?.navigate(AuthNavigationRoutes.Auth.title)
            authViewModel?.resetRegisterState()
        }
    }
}

@Composable
fun RegisterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Зарегистрироваться")
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}
