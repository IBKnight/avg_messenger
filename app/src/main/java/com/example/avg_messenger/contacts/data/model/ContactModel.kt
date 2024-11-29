package com.example.avg_messenger.contacts.data.model

import com.google.gson.annotations.SerializedName

data class ContactModel(
    @SerializedName("ID") val id: Int,
    @SerializedName("Login") val login: String,
    @SerializedName("UserName") val userName: String
)