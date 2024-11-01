package com.example.avg_messenger.chat_list.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.avg_messenger.chat_list.presentation.viewmodel.ChatListViewModel

@Composable
fun ChatListScreen(
    navController: NavController? = null,
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val chatList by viewModel.chatList.collectAsState()

    Scaffold(
        topBar = { AppBar() },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(chatList) { chat ->
                        Log.d("CHAT ID",chat.id.toString())
                        ChatItem(
                            chatName = chat.name,
                            onClick = {
                                // Обработчик клика по чату
                            }
                        )
                    }
                }
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(title = { Text(text = "Чатики") }, actions = {
        IconButton(onClick = { /* Действие для кнопки */ }) {
            Icon(Icons.Filled.Settings, contentDescription = "Настройки")
        }
    })
}

@Composable
fun ChatItem(chatName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Круглая иконка слева
            Icon(
                imageVector = Icons.Filled.Person, // Иконка пользователя
                contentDescription = "Иконка пользователя",
                modifier = Modifier
                    .size(60.dp) // Размер иконки
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape) // Круглый фон
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.onPrimary // Цвет иконки
            )

            Spacer(modifier = Modifier.width(16.dp)) // Отступ между иконкой и текстом

            // Название чата справа
            Text(
                text = chatName,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f) // Занимает оставшееся место
            )
        }
    }
}

