package com.example.avg_messenger.chat.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avg_messenger.chat.data.models.MessageModel
import com.example.avg_messenger.chat.domain.repository.ChatRepository
import com.example.avg_messenger.contacts.data.model.ContactModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatRepository: ChatRepository) : ViewModel() {

    private val _messages = MutableLiveData(emptyList<MessageModel>().toMutableList())
    val messages: LiveData<MutableList<MessageModel>> = _messages

    private var currentPage = 0

    private val _members = MutableStateFlow<List<ContactModel>>(emptyList())
    val members: StateFlow<List<ContactModel>> = _members


    init {
        Log.i("ChatViewModel", "Initializing message collection")
        viewModelScope.launch {
            chatRepository.messagesFlow
                .catch { exception -> Log.e("WebSocketChatScreen", "Exception: $exception") }
                .collect { newMessage ->
                    Log.d("WebSocketChatScreen", "Message: $newMessage")
                    _messages.value = _messages.value?.toMutableList()?.apply { add(newMessage) }
                }
        }
    }

    fun loadChatHistory(chatId: Int) {
        viewModelScope.launch {

            val history = chatRepository.fetchMessageHistory(chatId, currentPage)
            // Обновляем историю чата, добавляя старые сообщения в начало списка
            _messages.value = _messages.value?.toMutableList()?.apply { addAll(history) }
        }
    }

    fun getChatParticipant(chatId: Int) {
        viewModelScope.launch {

            val participant = chatRepository.fetchChatParticipant(chatId)

            _members.value = participant
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
