package com.example.avg_messenger.auth.data.model

import com.google.gson.annotations.SerializedName

data class AuthResponseModel(
    @SerializedName("UserId") val userId: Int,
    @SerializedName("Token") val tokenModel: TokenModel,
)