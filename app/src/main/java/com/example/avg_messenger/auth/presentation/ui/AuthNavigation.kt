import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.avg_messenger.auth.presentation.ui.AuthScreen
import com.example.avg_messenger.auth.presentation.ui.RegisterScreen
import com.example.avg_messenger.auth.presentation.ui.SplashScreen
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel

@Composable
fun MainNavigation(
    navController: NavHostController
) {
    val authViewModel = hiltViewModel<AuthViewModel>()

    NavHost(navController = navController, startDestination = AuthNavigationRoutes.Splash.title) {
        composable(AuthNavigationRoutes.Splash.title) { SplashScreen(authViewModel, navController) }
        composable(AuthNavigationRoutes.Auth.title) {
            AuthScreen(
                modifier = Modifier.padding(16.dp),
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(AuthNavigationRoutes.Register.title) {
            RegisterScreen(
                modifier = Modifier.padding(16.dp),
                authViewModel = authViewModel,
                navController = navController
            )
        }
    }
}

enum class AuthNavigationRoutes(val title: String) {
    Splash("splash"),
    Auth("auth"),
    Register("register"),
}