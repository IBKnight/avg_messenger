package com.example.avg_messenger.chat_list.presentation.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.avg_messenger.chat.presentation.ui.ChatActivity
import com.example.avg_messenger.chat_list.presentation.viewmodel.ChatListViewModel
import com.example.avg_messenger.common.components.AppBar

@Composable
fun ChatListScreen(
    navController: NavController? = null,
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val chatList by viewModel.chatList.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { AppBar(
            title = "Чатики",
            onClick = {}
        ) },
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
                        Log.d("CHAT ID", chat.id.toString())
                        ChatItem(
                            chatName = chat.name,
                            onClick = {
                                val intent = Intent(context, ChatActivity::class.java).apply {
                                    putExtra("CHAT_ID", chat.id)
                                }
                                context.startActivity(intent)

                            }
                        )
                    }
                }
            }
        }
    )
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
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ) // Круглый фон
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

