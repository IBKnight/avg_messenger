package com.example.avg_messenger.contacts.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avg_messenger.contacts.data.model.ContactModel
import com.example.avg_messenger.contacts.data.model.CreateContactModel
import com.example.avg_messenger.contacts.domain.repositories.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    // Список контактов
    private val _contactsList = MutableStateFlow<List<ContactModel>>(emptyList())
    val contactsList: StateFlow<List<ContactModel>> = _contactsList

    // Статус загрузки
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Ошибки
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadContacts()
    }

    // Загрузка всех контактов
    private fun loadContacts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val contacts = contactsRepository.getAllContacts()
                _contactsList.value = contacts
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки контактов: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Создание нового контакта с именем
    fun createContact(contactLogin: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val contact = CreateContactModel(contactLogin)
                val response = contactsRepository.createContact(contact)
                if (response.isSuccessful)
                    loadContacts()
                else
                    _errorMessage.value = "Данного контакта не существует"
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка создания контакта: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Получение контакта по ID (возвращает StateFlow для асинхронного доступа)
//    fun getContactById(contactId: Int): StateFlow<ContactModel?> {
//        val contactState = MutableStateFlow<ContactModel?>(null)
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val contact = contactsRepository.getContactById(contactId)
//                contactState.value = contact
//            } catch (e: Exception) {
//                _errorMessage.value = "Ошибка получения контакта: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//        return contactState
//    }

    // Удаление контакта
    fun deleteContactById(contactId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = contactsRepository.deleteContactById(contactId)
                if (response.isSuccessful)
                    loadContacts()
                else
                    _errorMessage.value = "Ошибка удаления"
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка удаления контакта: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Сброс ошибок
    fun clearError() {
        _errorMessage.value = null
    }
}
