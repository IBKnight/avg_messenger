package com.example.avg_messenger.user.data.datasource

import com.example.avg_messenger.user.data.models.UserCreationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserDataSource {
    @POST("user/set/username")
    suspend fun setUsername(@Body username: UserCreationModel): Response<Void>
}