package com.example.avg_messenger.chat_list.data.datasources

import com.example.avg_messenger.chat_list.data.models.ChatCreationModel
import com.example.avg_messenger.chat_list.data.models.ChatModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatListRemoteDataSource {
    @GET("chat/all")
    suspend fun getAllChats(): List<ChatModel>

    @POST("chat/")
    suspend fun createChat(
        @Body chatOptions: ChatCreationModel
    ): Response<Void>

}



