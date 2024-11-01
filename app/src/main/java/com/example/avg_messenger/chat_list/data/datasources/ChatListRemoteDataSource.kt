package com.example.avg_messenger.chat_list.data.datasources

import com.example.avg_messenger.chat_list.data.models.ChatModel
import retrofit2.http.GET

interface ChatListRemoteDataSource {
    @GET("chat/all")
    suspend fun getAllChats(): List<ChatModel>

}


