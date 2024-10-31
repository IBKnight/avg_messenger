package com.example.avg_messenger.auth.data.model

import com.google.gson.annotations.SerializedName

data class RefreshRequest(
    @SerializedName("token") val refreshToken: String,
    @SerializedName("user_id") val userId: Int,
)