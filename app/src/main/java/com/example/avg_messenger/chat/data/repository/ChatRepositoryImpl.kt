package com.example.avg_messenger.chat.data.repository

import android.util.Log
import com.example.avg_messenger.chat.data.datasources.ChatWsDatasource
import com.example.avg_messenger.chat.data.datasources.MessageHistoryDataSource
import com.example.avg_messenger.chat.data.models.MessageHistoryModel
import com.example.avg_messenger.chat.data.models.MessageModel
import com.example.avg_messenger.chat.domain.repository.ChatRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class ChatRepositoryImpl @Inject constructor(
    private val chatWsDatasource: ChatWsDatasource,
    private val messageHistoryDataSource: MessageHistoryDataSource
) : ChatRepository {
    override val messagesFlow: Flow<MessageModel> = chatWsDatasource.messagesFlow

    override suspend fun fetchMessageHistory(chatId: Int, pageId: Int): List<MessageHistoryModel> {
        val result = messageHistoryDataSource.getMessages(chatId, pageId)

        Log.i("fetchMessageHistory", result.toString())
        return result
    }

    override fun sendMessage(text: String) = chatWsDatasource.sendMessage(text)

    override fun connectToChat(chatId: Int) = chatWsDatasource.connect(chatId)

    override fun disconnectFromChat() = chatWsDatasource.disconnect()
}

