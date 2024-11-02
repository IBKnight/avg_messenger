package com.example.avg_messenger.chat_list.data.repositories

import com.example.avg_messenger.chat_list.data.datasources.ChatListRemoteDataSource
import com.example.avg_messenger.chat_list.data.models.ChatModel
import com.example.avg_messenger.chat_list.domain.repository.ChatsListRepository
import javax.inject.Inject

class ChatsListRepositoryImpl @Inject constructor(
    private val chatListRemoteDataSource: ChatListRemoteDataSource,
) : ChatsListRepository {
    override suspend fun getChats(): List<ChatModel> {
        return chatListRemoteDataSource.getAllChats()
    }

}