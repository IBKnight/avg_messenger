package com.example.avg_messenger.chat_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avg_messenger.chat_list.data.models.ChatCreationModel
import com.example.avg_messenger.chat_list.data.models.ChatModel
import com.example.avg_messenger.chat_list.domain.repository.ChatsListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val chatsListRepository: ChatsListRepository
) : ViewModel() {

    private val _chatList = MutableStateFlow<List<ChatModel>>(emptyList())
    val chatList: StateFlow<List<ChatModel>> = _chatList

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            _chatList.value = chatsListRepository.getChats()
        }
    }

    fun createChat(
        chatOptions: ChatCreationModel
    ) {
        viewModelScope.launch {
            val response = chatsListRepository.createChat(chatOptions)
            if (response.isSuccessful)
                loadChats()
        }
    }
}
