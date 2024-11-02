package com.example.avg_messenger.auth.data.model

import com.google.gson.annotations.SerializedName

data class RefreshResponse(
    //TODO(поменять название на access_token после обновления апишки)
    @SerializedName("refresh_token") val accessToken: String
)
