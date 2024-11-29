package com.example.avg_messenger.chat.data.datasources

import com.example.avg_messenger.contacts.data.model.ContactModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatDatasource {
    @GET("chat/members/{id}")
    suspend fun getChatMembers(
        @Path("id") chatId: Int,
    ): List<ContactModel>

}