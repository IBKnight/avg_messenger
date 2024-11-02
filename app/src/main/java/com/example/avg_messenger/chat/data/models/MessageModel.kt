package com.example.avg_messenger.chat.data.models

data class MessageModel (
    val id: Int,
    val chatId: Int,
    val senderId: Int,
    val userName: String,
    val sendingTime: String,
    val text: String
)