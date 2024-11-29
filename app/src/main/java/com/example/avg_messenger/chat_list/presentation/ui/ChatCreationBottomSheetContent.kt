package com.example.avg_messenger.chat_list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.avg_messenger.contacts.presentation.viewmodel.ContactsViewModel


@Composable
fun ContactSelectionSheet(
    onDismiss: () -> Unit,
    onCreateChat: (List<Int>) -> Unit,
    contactsViewModel: ContactsViewModel,
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
) {
    val contacts = contactsViewModel.contactsList.collectAsState()
    val selectedContacts = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text("Выберите контакты для чата", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onTextFieldValueChange,
            label = { Text("Введите название чата") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Введите название чата") },
            shape = RoundedCornerShape(16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts.value) { contact ->
                Card {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (selectedContacts.contains(contact.id)) {
                                selectedContacts.remove(contact.id)
                            } else {
                                selectedContacts.add(contact.id)
                            }
                        }
                        .padding(8.dp)) {

                        Text(
                            text = contact.userName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface // Цвет текста
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (selectedContacts.contains(contact.id)) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = "Выбран",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onCreateChat(selectedContacts) }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Создать чат")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

