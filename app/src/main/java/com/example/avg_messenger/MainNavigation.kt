package android.template.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.avg_messenger.auth.presentation.ui.AuthScreen
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val authViewModel = hiltViewModel<AuthViewModel>()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { AuthScreen(modifier = Modifier.padding(16.dp),
            authViewModel = authViewModel)
        }
        // TODO: Add more destinations
    }
}