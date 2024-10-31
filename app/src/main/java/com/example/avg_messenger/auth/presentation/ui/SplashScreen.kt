package com.example.avg_messenger.auth.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.avg_messenger.auth.domain.model.AuthState
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel

@Composable
fun SplashScreen(viewModel: AuthViewModel, navController: NavController) {
    LaunchedEffect(viewModel) {
        viewModel.checkTokenAndLogin()
        viewModel.authState.collect { authState ->
            when (authState) {
                is AuthState.Success -> navController.navigate(NavigationRoutes.ChatsList.title)

                is AuthState.Error -> navController.navigate(NavigationRoutes.Auth.title)

                else -> {}
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
