package com.example.avg_messenger.chat_list.presentation.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.avg_messenger.chat.presentation.ui.ChatActivity
import com.example.avg_messenger.chat_list.data.models.ChatCreationModel
import com.example.avg_messenger.chat_list.presentation.viewmodel.ChatListViewModel
import com.example.avg_messenger.common.components.AppBar
import com.example.avg_messenger.contacts.presentation.viewmodel.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController? = null,
    viewModel: ChatListViewModel = hiltViewModel(),
) {
    val chatList by viewModel.chatList.collectAsState()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val contactsViewModel = hiltViewModel<ContactsViewModel>()
    var textFieldBottomSheetValue by remember { mutableStateOf(TextFieldValue()) }



    Scaffold(topBar = {
        AppBar(title = "Чатики", onClick = {})
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                showBottomSheet = true
            }, modifier = Modifier.padding(vertical = 72.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Создать чат")
        }
    }, content = { paddingValues ->
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 72.dp)

            ) {
                items(chatList) { chat ->
                    Log.d("CHAT ID", chat.id.toString())
                    ChatItem(chatName = chat.name, onClick = {
                        val intent = Intent(context, ChatActivity::class.java).apply {
                            putExtra("CHAT_ID", chat.id)
                        }
                        context.startActivity(intent)

                    })
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(

                    modifier = Modifier.navigationBarsPadding(), // Добавляем отступы
                    onDismissRequest = {
                        showBottomSheet = false
                    }, sheetState = sheetState
                ) {

                    ContactSelectionSheet(

                        onDismiss = {},
                        onCreateChat = { members ->
                            viewModel.createChat(
                                ChatCreationModel(
                                    isDirect = false,
                                    membersIds = members,
                                    name = textFieldBottomSheetValue.text
                                )
                            )
                            showBottomSheet = false
                        },
                        textFieldValue = textFieldBottomSheetValue,
                        onTextFieldValueChange = { textFieldBottomSheetValue = it },
                        contactsViewModel = contactsViewModel
                    )

                }
            }
        }
    })
}
