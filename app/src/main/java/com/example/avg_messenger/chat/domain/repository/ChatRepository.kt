package com.example.avg_messenger.chat.domain.repository

import com.example.avg_messenger.chat.data.models.MessageModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    val messagesFlow: Flow<MessageModel>

    suspend fun fetchMessageHistory(chatId: Int, pageId: Int): List<MessageModel>

    fun sendMessage(text: String)

    fun connectToChat(chatId: Int)

    fun disconnectFromChat()
}