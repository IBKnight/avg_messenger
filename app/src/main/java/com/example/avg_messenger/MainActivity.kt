package com.example.avg_messenger

import android.os.Bundle
import android.template.ui.MainNavigation
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.avg_messenger.auth.presentation.ui.AuthScreen
import com.example.avg_messenger.ui.theme.Avg_messengerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Avg_messengerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation()
                    print(innerPadding)
                }
            }
        }
    }
}
