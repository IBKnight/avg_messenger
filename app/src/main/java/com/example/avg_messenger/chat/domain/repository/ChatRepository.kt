package com.example.avg_messenger.chat.domain.repository

import com.example.avg_messenger.chat.data.models.MessageHistoryModel
import com.example.avg_messenger.chat.data.models.MessageModel
import com.example.avg_messenger.contacts.data.model.ContactModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    val messagesFlow: Flow<MessageModel>

    suspend fun fetchMessageHistory(chatId: Int, pageId: Int): List<MessageHistoryModel>

    suspend fun fetchChatParticipant(chatId: Int): List<ContactModel>

    fun sendMessage(text: String)

    fun connectToChat(chatId: Int)

    fun disconnectFromChat()
}