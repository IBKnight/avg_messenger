package com.example.avg_messenger.contacts.presentation.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.avg_messenger.chat.presentation.ui.ChatActivity
import com.example.avg_messenger.common.components.AppBar
import com.example.avg_messenger.contacts.data.model.ContactModel
import com.example.avg_messenger.contacts.presentation.viewmodel.ContactsViewModel

@Composable
fun ContactsScreen(
    navController: NavController,
    viewModel: ContactsViewModel = hiltViewModel(),
) {
    val contacts by viewModel.contactsList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Фильтруем контакты по запросу
    val filteredContacts = contacts.filter { contact ->
        contact.userName.contains(searchQuery.text, ignoreCase = true)
    }

    // Обработка ошибок через Snackbar
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }, // Подключаем SnackbarHost
        topBar = {
            AppBar(
                title = "Контакты",
                onClick = {}
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Поле поиска
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Поиск...") },
                    textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
                    shape = RoundedCornerShape(16.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка добавления контакта
                Button(
                    onClick = {
                        viewModel.createContact(searchQuery.text)
                    },
                    enabled = searchQuery.text.isNotBlank() && !filteredContacts.any { it.userName == searchQuery.text },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Добавить контакт: ${searchQuery.text}")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Список контактов
                LazyColumn(contentPadding = PaddingValues(bottom = 72.dp)) {
                    items(filteredContacts) { contact ->
                        ContactItem(
                            contact = contact,
                            onContactClick = {
//                                val intent = Intent(context, ChatActivity::class.java).apply {
//                                    putExtra("CONTACT_NAME", contact.login)
//                                }
//                                context.startActivity(intent)
                            },
                            onDeleteClick = {
                                viewModel.deleteContactById(contact.id) // Удаление контакта
                            }
                        )
                    }
                }
                SnackbarHost(hostState = snackbarHostState)

            }
        }
    )
}
