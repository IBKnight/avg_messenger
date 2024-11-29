package com.example.avg_messenger.chat_list.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.avg_messenger.contacts.presentation.ui.ContactsScreen

@Composable
fun ChatNavigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = ChatNavigationRoutes.ChatsList.title
    ) {
        composable(ChatNavigationRoutes.ChatsList.title) { ChatListScreen(navController = navController) }
        composable(ChatNavigationRoutes.Contacts.title) { ContactsScreen(navController = navController) }

    }
}

enum class ChatNavigationRoutes(val title: String) {
    ChatsList("chatsList"),
    Contacts("contacts"),
}