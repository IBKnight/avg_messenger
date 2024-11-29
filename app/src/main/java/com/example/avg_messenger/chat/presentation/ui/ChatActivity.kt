package com.example.avg_messenger.chat.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avg_messenger.chat.presentation.ChatViewModel
import com.example.avg_messenger.ui.theme.Avg_messengerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showDialog by remember { mutableStateOf(false) }
            val chatViewModel = hiltViewModel<ChatViewModel>()
            val contacts by chatViewModel.members.collectAsState()


            Avg_messengerTheme {
                Scaffold(topBar = {
                    TopAppBar(title = { Text("Chat") }, navigationIcon = {
                        IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                            )
                        }
                    }, actions = {
                        IconButton(onClick = {
                            chatViewModel.getChatParticipant(intent.getIntExtra("CHAT_ID", -1))
                            showDialog = true
                        }) {
                            Icon(
                                Icons.Outlined.Info, contentDescription = "Back"
                            )
                        }
                    })
                }) { innerPadding ->
                    ChatScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        chatId = intent.getIntExtra("CHAT_ID", -1)
                    )

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            confirmButton = {},
                            title = { Text("Участники беседы") },
                            text = {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 300.dp) // Ограничение высоты для прокрутки
                                ) {
                                    items(contacts) { member ->
                                        Text(
                                            text = member.userName, // Используйте имя участника
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                        )
                                    }
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}

