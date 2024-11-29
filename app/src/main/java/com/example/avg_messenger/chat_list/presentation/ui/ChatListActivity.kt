package com.example.avg_messenger.chat_list.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.avg_messenger.auth.presentation.ui.AuthActivity
import com.example.avg_messenger.auth.presentation.viewmodel.AuthViewModel
import com.example.avg_messenger.ui.theme.Avg_messengerTheme
import com.example.avg_messenger.user.presentation.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Avg_messengerTheme {
                var selectedItem by remember { mutableIntStateOf(0) }
                val drawerState by remember { mutableStateOf(DrawerValue.Closed) }
                var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
                val context = LocalContext.current
                val authViewModel = hiltViewModel<AuthViewModel>()
                val userViewModel = hiltViewModel<UserViewModel>()


                val navController = rememberNavController()
                ModalNavigationDrawer(
                    drawerState = rememberDrawerState(drawerState),
                    drawerContent = {
                        // Вызов вашего компонента DrawerContent
                        ModalDrawerSheet(
                            modifier = Modifier
                        ){

                            DrawerContent(
                                textFieldValue = textFieldValue,
                                onTextFieldValueChange = { textFieldValue = it },
                                onExitClick = {
                                    authViewModel.logout()
                                    val intent = Intent(context, AuthActivity::class.java)
                                    context.startActivity(intent)
                                },
                                onSaveClick = {
                                    userViewModel.setUsername(textFieldValue.text)
                                    println("Новый имя: ${textFieldValue.text}")
                                    textFieldValue = textFieldValue.copy("")
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            NavigationBar() {
                                NavigationBarItem(

                                    icon = {
                                        Icon(
                                            Icons.Filled.Email,
                                            contentDescription = "Чаты"
                                        )
                                    },
                                    label = { Text("Чаты") },
                                    selected = selectedItem == 0,
                                    onClick = {
                                        selectedItem = 0
                                        navController.navigate(ChatNavigationRoutes.ChatsList.title)
                                    }
                                )
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Filled.Person,
                                            contentDescription = "Контакты"
                                        )
                                    },
                                    label = { Text("Контакты") },
                                    selected = selectedItem == 1,
                                    onClick = {
                                        selectedItem = 1
                                        navController.navigate(ChatNavigationRoutes.Contacts.title)
                                    }
                                )
                            }
                        },
                    ) {
                        ChatNavigation(navController = navController)
                    }
                }
            }
        }
    }
}


@Composable
fun DrawerContent(
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    onExitClick: () -> Unit,
    onSaveClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text("Меню", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onTextFieldValueChange,
            label = { Text("Введите новое имя") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Введите новое имя") },
            shape = RoundedCornerShape(16.dp),
            )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onExitClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Выйти")
        }
    }

}
