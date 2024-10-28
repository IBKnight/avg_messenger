package com.example.avg_messenger.auth.di

import com.example.avg_messenger.BuildConfig
import com.example.avg_messenger.auth.data.datasources.AuthRemoteDataSource
import com.example.avg_messenger.auth.data.repository.AuthRepositoryImpl
import com.example.avg_messenger.auth.domain.repository.AuthRepository

import com.example.avg_messenger.auth.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    private const val apiUrl = BuildConfig.BASE_URL


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthRemoteDataSource {
        return retrofit.create(AuthRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
}
