package com.example.avg_messenger.user.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avg_messenger.contacts.data.model.CreateContactModel
import com.example.avg_messenger.user.data.models.UserCreationModel
import com.example.avg_messenger.user.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun setUsername(newUsername: String) {
        viewModelScope.launch {
            try {
                userRepository.setUsername(UserCreationModel(newUsername))
            } catch (e: Exception) {
                Log.i("setUsername", "Ошибка создания контакта: ${e.message}")
            }
        }
    }
}