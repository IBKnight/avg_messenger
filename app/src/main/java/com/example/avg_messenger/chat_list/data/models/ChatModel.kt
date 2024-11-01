package com.example.avg_messenger.chat_list.data.models
import com.example.avg_messenger.auth.data.model.User
import com.google.gson.annotations.SerializedName


data class ChatModel (
    @SerializedName("id") val id: Int,
    @SerializedName("IsDirect") val isDirect: Boolean,
    @SerializedName("Name") val name: String,
    @SerializedName("Owner") val owner: User,
    @SerializedName("OwnerId") val ownerId: Int
)