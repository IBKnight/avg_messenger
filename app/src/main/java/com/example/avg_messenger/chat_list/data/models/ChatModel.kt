package com.example.avg_messenger.chat_list.data.models
import com.example.avg_messenger.auth.data.model.User


data class ChatModel (
    val id: Int,
    val isDirect: Boolean,
    val name: String,
    val owner: User,
    val ownerId: Int
)