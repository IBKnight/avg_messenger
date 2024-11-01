package com.example.avg_messenger.chat_list.domain.repository

import com.example.avg_messenger.chat_list.data.models.ChatModel

interface ChatRepository {
    suspend fun getChats(): List<ChatModel>
}
