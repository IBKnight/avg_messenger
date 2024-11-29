package com.example.avg_messenger.user.domain

import com.example.avg_messenger.user.data.models.UserCreationModel
import retrofit2.Response

interface UserRepository {
    suspend fun setUsername(username: UserCreationModel): Response<Void>
}