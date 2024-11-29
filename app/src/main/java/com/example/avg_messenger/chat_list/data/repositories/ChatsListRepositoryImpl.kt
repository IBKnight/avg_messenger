package com.example.avg_messenger.chat_list.data.repositories

import com.example.avg_messenger.auth.data.TokenManager
import com.example.avg_messenger.chat_list.data.datasources.ChatListRemoteDataSource
import com.example.avg_messenger.chat_list.data.models.ChatCreationModel
import com.example.avg_messenger.chat_list.data.models.ChatModel
import com.example.avg_messenger.chat_list.domain.repository.ChatsListRepository
import retrofit2.Response
import javax.inject.Inject

class ChatsListRepositoryImpl @Inject constructor(
    private val chatListRemoteDataSource: ChatListRemoteDataSource,
) : ChatsListRepository {
    override suspend fun getChats(): List<ChatModel> {
        return chatListRemoteDataSource.getAllChats()
    }

    override suspend fun createChat(chatOptions: ChatCreationModel): Response<Void> {
        return chatListRemoteDataSource.createChat(chatOptions)
    }
}