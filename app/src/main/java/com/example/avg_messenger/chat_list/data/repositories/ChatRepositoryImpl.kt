package com.example.avg_messenger.chat_list.data.repositories

import com.example.avg_messenger.chat_list.data.datasources.ChatListRemoteDataSource
import com.example.avg_messenger.chat_list.data.models.ChatModel
import com.example.avg_messenger.chat_list.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatListRemoteDataSource: ChatListRemoteDataSource,
) : ChatRepository {
    override suspend fun getChats(): List<ChatModel> {
        return chatListRemoteDataSource.getAllChats()
    }

}