package com.example.avg_messenger.contacts.data

import android.util.Log
import com.example.avg_messenger.contacts.data.datasources.ContactsRemoteDataSource
import com.example.avg_messenger.contacts.data.model.ContactModel
import com.example.avg_messenger.contacts.data.model.CreateContactModel
import com.example.avg_messenger.contacts.domain.repositories.ContactsRepository
import com.google.gson.Gson
import retrofit2.Response

import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactsRemoteDataSource: ContactsRemoteDataSource
) : ContactsRepository {

    override suspend fun getAllContacts(): List<ContactModel> {
        // Делегируем вызов к удаленному источнику данных
        return contactsRemoteDataSource.getAllContacts()
    }

    override suspend fun createContact(contact: CreateContactModel): Response<Unit> {
        // Логируем начало вызова
        Log.i("createContact", "Вызов добавления")

        // Выполняем вызов и сохраняем результат
        val response = contactsRemoteDataSource.createContact(contact)

        // Логируем результат
        Log.i("createContact", "Результат вызова: $response")

        // Возвращаем результат
        return response
    }


    override suspend fun getContactById(contactId: Int): ContactModel {
        // Делегируем вызов к удаленному источнику данных
        return contactsRemoteDataSource.getContactById(contactId)
    }

    override suspend fun deleteContactById(contactId: Int): Response<Unit> {
        // Делегируем вызов к удаленному источнику данных
        return contactsRemoteDataSource.deleteContactById(contactId)
    }
}
