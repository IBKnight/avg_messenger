package com.example.avg_messenger.auth.presentation.ui

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avg_messenger.auth.domain.model.AuthState
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel
import com.example.avg_messenger.chat_list.presentation.ui.ChatListActivity

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    authViewModel: AuthViewModel? = null
) {

    val authState =
        authViewModel?.authState?.collectAsState() ?: remember { mutableStateOf(AuthState.Idle) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

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

        LoginInput(email) { email = it }
        Spacer(modifier = Modifier.height(8.dp))
        PasswordInput(password) { password = it }

        Spacer(modifier = Modifier.height(16.dp))

        when (authState.value) {
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }

            else -> {
                AuthButton {
                    authViewModel?.login(email, password)

                    Log.i("MyTag", authState.value.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton (onClick = { navController?.navigate("register") }) {
            Text(text = "Зарегистрироваться")
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
    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Success) {
            val intent = Intent(context, ChatListActivity::class.java)
            context.startActivity(intent)
//            navController?.navigate(AuthNavigationRoutes.ChatsList.title)
        }
    }


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
