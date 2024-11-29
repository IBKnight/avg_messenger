package com.example.avg_messenger.chat_list.domain.repository

import com.example.avg_messenger.chat_list.data.models.ChatCreationModel
import com.example.avg_messenger.chat_list.data.models.ChatModel
import retrofit2.Response

interface ChatsListRepository {
    suspend fun getChats(): List<ChatModel>
    suspend fun createChat(chatOptions: ChatCreationModel): Response<Void>
}
