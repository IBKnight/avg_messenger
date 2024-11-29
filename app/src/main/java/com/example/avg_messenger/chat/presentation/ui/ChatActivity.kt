package com.example.avg_messenger.chat.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.avg_messenger.chat.presentation.ChatViewModel
import com.example.avg_messenger.common.DateUtils
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
            Avg_messengerTheme {
                Scaffold(topBar = {
                    TopAppBar(title = { Text("Chat") }, navigationIcon = {
                        IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                            )
                        }
                    }, actions = {
                        IconButton(onClick = { }) {
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
                }
            }
        }
    }
}

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier, chatId: Int, viewModel: ChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.connectToChat(chatId)
        viewModel.loadChatHistory(chatId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //TODO сделать чтобы если один человек пишет несколько сообщений,
        // то только над первым пишется ник
        LazyColumn(
            modifier = Modifier.weight(1f), reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                Log.i("messages ui", message.toString())
                MessageItem(
                    userName = message.userName,
                    text = message.text,
                    sendingTime = message.sendingTime
                )
            }

        }

        MessageInput(viewModel = viewModel)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.disconnectFromChat()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(viewModel: ChatViewModel) {
    var inputText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                .height(56.dp)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(text = "Введите сообщение...", color = Color.Gray) },
            textStyle = TextStyle(
                fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface
            )
        )

        IconButton(
            onClick = {
                if (inputText.isNotBlank()) {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                }
            }, modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun MessageItem(
    userName: String, text: String, sendingTime: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Иконка пользователя",
            modifier = Modifier
                .size(48.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(8.dp) // Внутренний отступ для текста сообщения
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = DateUtils.formatTime(sendingTime),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.End),
                color = Color.Gray
            )
        }
    }
}
