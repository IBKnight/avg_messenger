import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.avg_messenger.auth.presentation.ui.AuthScreen
import com.example.avg_messenger.auth.presentation.ui.SplashScreen
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel
import com.example.avg_messenger.chat_list.presentation.ui.ChatListScreen

@Composable
fun MainNavigation(
    navController: NavHostController
) {
    val authViewModel = hiltViewModel<AuthViewModel>()

    NavHost(navController = navController, startDestination = NavigationRoutes.Splash.title) {
        composable(NavigationRoutes.Splash.title) { SplashScreen(authViewModel, navController) }
        composable(NavigationRoutes.Auth.title) {
            AuthScreen(
                modifier = Modifier.padding(16.dp),
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(NavigationRoutes.ChatsList.title) { ChatListScreen(navController = navController) }

    }
}

enum class NavigationRoutes(val title: String){
    Splash("splash"),
    Auth("auth"),
    ChatsList("chatsList"),
}