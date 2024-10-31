package com.example.avg_messenger.chat_list.data

import com.example.avg_messenger.chat_list.data.models.ChatModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatListRemoteDataSource {
    @GET("chat/all")
    suspend fun getAllChats(): List<ChatModel>

}


