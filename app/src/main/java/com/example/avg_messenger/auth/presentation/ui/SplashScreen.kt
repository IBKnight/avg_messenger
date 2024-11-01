package com.example.avg_messenger.auth.presentation.ui

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.avg_messenger.auth.domain.model.AuthState
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel
import com.example.avg_messenger.chat_list.presentation.ui.ChatActivity

@Composable
fun SplashScreen(viewModel: AuthViewModel, navController: NavController) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.checkTokenAndLogin()
        viewModel.authState.collect { authState ->
            val intent = Intent(context, ChatActivity::class.java)
            when (authState) {

                is AuthState.Success -> context.startActivity(intent)

                is AuthState.Error -> navController.navigate(AuthNavigationRoutes.Auth.title)

                else -> {}
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
