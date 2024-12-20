package com.example.avg_messenger.chat.data.datasources


import com.example.avg_messenger.chat.data.models.MessageHistoryModel
import com.example.avg_messenger.contacts.data.model.ContactModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MessageHistoryDataSource {
    @GET("chat/messages")
    suspend fun getMessages(
        @Query("chat-id") chatId: Int,
        @Query("page-id") pageId: Int
    ): List<MessageHistoryModel>
}
