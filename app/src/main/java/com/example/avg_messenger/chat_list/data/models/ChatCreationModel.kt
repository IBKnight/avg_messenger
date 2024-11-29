package com.example.avg_messenger.chat_list.data.models

import com.google.gson.annotations.SerializedName

data class ChatCreationModel(
    @SerializedName("is_direct") val isDirect: Boolean,
    @SerializedName("members_ids") val membersIds: List<Int>,
    val name: String,
)
