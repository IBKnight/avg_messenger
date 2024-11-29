package com.example.avg_messenger.contacts.data.datasources

import com.example.avg_messenger.contacts.data.model.ContactModel
import com.example.avg_messenger.contacts.data.model.CreateContactModel
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ContactsRemoteDataSource {
    @GET("contact/all")
    suspend fun getAllContacts(): List<ContactModel>

    @POST("contact/")
    suspend fun createContact(
        @Body contact: CreateContactModel
    ): Response<Unit>

    @GET("contact/{id}/")
    suspend fun getContactById(
        @Path("id") chatId: Int,
    ): ContactModel

    @DELETE("contact/{id}")
    suspend fun deleteContactById(
        @Path("id") chatId: Int,
    ): Response<Unit>

}
