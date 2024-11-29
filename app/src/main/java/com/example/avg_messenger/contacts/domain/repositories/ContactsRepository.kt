package com.example.avg_messenger.contacts.domain.repositories

import com.example.avg_messenger.contacts.data.model.ContactModel
import com.example.avg_messenger.contacts.data.model.CreateContactModel
import retrofit2.Response


interface ContactsRepository {

    /**
     * Получение всех контактов.
     * @return список контактов [ContactModel].
     */
    suspend fun getAllContacts(): List<ContactModel>

    /**
     * Создание нового контакта.
     * @return объект [Response], содержащий информацию о результате.
     */
    suspend fun createContact(contact: CreateContactModel) : Response<Unit>

    /**
     * Получение контакта по идентификатору.
     * @param contactId идентификатор контакта.
     * @return объект [ContactModel].
     */
    suspend fun getContactById(contactId: Int): ContactModel

    /**
     * Удаление контакта по идентификатору.
     * @param contactId идентификатор контакта.
     * @return объект [Response], содержащий информацию о результате.
     */
    suspend fun deleteContactById(contactId: Int): Response<Unit>
}
