package com.example.avg_messenger.user.data

import com.example.avg_messenger.chat_list.data.datasources.ChatListRemoteDataSource
import com.example.avg_messenger.chat_list.domain.repository.ChatsListRepository
import com.example.avg_messenger.user.data.datasource.UserDataSource
import com.example.avg_messenger.user.data.models.UserCreationModel
import com.example.avg_messenger.user.domain.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun setUsername(username: UserCreationModel): Response<Void> {
        return userDataSource.setUsername(username)
    }
}