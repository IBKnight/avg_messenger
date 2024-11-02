package com.example.avg_messenger.chat.data.models

data class ChatMessageModel(
    override val id: Int,
    override val chatId: Int,
    override val senderId: Int,
    override val userName: String,
    override val sendingTime: String,
    override val text: String
) : MessageModel {

}