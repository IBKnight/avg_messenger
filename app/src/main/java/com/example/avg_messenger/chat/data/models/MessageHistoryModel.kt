package com.example.avg_messenger.chat.data.models

import com.google.gson.annotations.SerializedName

data class MessageHistoryModel (
    @SerializedName("ID") override val id: Int,
    @SerializedName("ChatId") override val chatId: Int,
    @SerializedName("SenderId") override val senderId: Int,
    @SerializedName("UserName") override val userName: String,
    @SerializedName("SendingTime") override val sendingTime: String,
    @SerializedName("Text") override val text: String
): MessageModel






