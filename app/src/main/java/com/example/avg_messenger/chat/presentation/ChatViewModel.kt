package com.example.avg_messenger.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avg_messenger.chat.data.models.MessageModel
import com.example.avg_messenger.chat.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatRepository: ChatRepository) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> = _messages

    private var currentPage = 1

    init {
        chatRepository.messagesFlow.onEach { newMessage ->
            // Создаем новый список, добавляя новое сообщение к существующему списку
            _messages.value = _messages.value + newMessage
        }.launchIn(viewModelScope)
    }

    fun loadChatHistory(chatId: Int) {
        viewModelScope.launch {
            val history = chatRepository.fetchMessageHistory(chatId, currentPage)
            // Обновляем историю чата, добавляя старые сообщения в начало списка
            _messages.value = history + _messages.value
        }
    }

    fun sendMessage(text: String) {
        chatRepository.sendMessage(text)
    }

    fun connectToChat(chatId: Int) {
        chatRepository.connectToChat(chatId)
    }

    fun disconnectFromChat() {
        chatRepository.disconnectFromChat()
    }
}
