package com.example.avg_messenger.di

import com.example.avg_messenger.auth.data.AuthInterceptor
import android.content.Context
import android.content.SharedPreferences
import com.example.avg_messenger.BuildConfig
import com.example.avg_messenger.auth.data.TokenManager
import com.example.avg_messenger.auth.data.datasources.AuthRemoteDataSource
import com.example.avg_messenger.auth.data.repository.AuthRepositoryImpl
import com.example.avg_messenger.auth.domain.repository.AuthRepository
import com.example.avg_messenger.chat.data.datasources.ChatWsDatasource
import com.example.avg_messenger.chat.data.datasources.MessageHistoryDataSource
import com.example.avg_messenger.chat.data.repository.ChatRepositoryImpl
import com.example.avg_messenger.chat.domain.repository.ChatRepository
import com.example.avg_messenger.chat_list.data.datasources.ChatListRemoteDataSource
import com.example.avg_messenger.chat_list.data.repositories.ChatsListRepositoryImpl
import com.example.avg_messenger.chat_list.domain.repository.ChatsListRepository
import com.example.avg_messenger.contacts.data.ContactsRepositoryImpl
import com.example.avg_messenger.contacts.data.datasources.ContactsRemoteDataSource
import com.example.avg_messenger.contacts.domain.repositories.ContactsRepository
import com.example.avg_messenger.user.data.UserRepositoryImpl
import com.example.avg_messenger.user.data.datasource.UserDataSource
import com.example.avg_messenger.user.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    private const val apiUrl = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        return TokenManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenManager: TokenManager,
    ): AuthInterceptor {
        return AuthInterceptor(
            tokenManager
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWebSocketDataSource(okHttpClient: OkHttpClient): ChatWsDatasource {
        return ChatWsDatasource(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(retrofit: Retrofit): UserDataSource {
        return retrofit.create(UserDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userRemoteDataSource: UserDataSource,
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideContactsRemoteDataSource(retrofit: Retrofit): ContactsRemoteDataSource {
        return retrofit.create(ContactsRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideContactsRepository(
        contactsRemoteDataSource: ContactsRemoteDataSource,
    ): ContactsRepository {
        return ContactsRepositoryImpl(contactsRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideMessageHistoryDataSource(retrofit: Retrofit): MessageHistoryDataSource {
        return retrofit.create(MessageHistoryDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        chatWsDatasource: ChatWsDatasource,
        messageHistoryDataSource: MessageHistoryDataSource
    ): ChatRepository {
        return ChatRepositoryImpl(chatWsDatasource, messageHistoryDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(retrofit: Retrofit): AuthRemoteDataSource {
        return retrofit.create(AuthRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideChatListDatasource(retrofit: Retrofit): ChatListRemoteDataSource {
        return retrofit.create(ChatListRemoteDataSource::class.java)
    }


    @Provides
    @Singleton
    fun provideChatListRepository(
        chatListRemoteDataSource: ChatListRemoteDataSource,
    ): ChatsListRepository {
        return ChatsListRepositoryImpl(chatListRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        chatListRemoteDataSource: ChatListRemoteDataSource,
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource, chatListRemoteDataSource, tokenManager)
    }


}
