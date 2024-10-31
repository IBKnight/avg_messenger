package com.example.avg_messenger.auth.data.model

import com.google.gson.annotations.SerializedName

data class TokenModel (
    @SerializedName("AccessToken") val accessToken: String,
    @SerializedName("RefreshToken") val refreshToken: String,
)