package com.example.avg_messenger.auth.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val KEY_ACCESS_TOKEN = "accessToken"
        private const val KEY_REFRESH_TOKEN = "refreshToken"
        private const val KEY_USER_ID = "userId"
    }

    /**
     * Сохраняет accessToken, refreshToken и userId.
     */
    fun saveTokens(accessToken: String, refreshToken: String, userId: Int) {
        sharedPreferences.edit().apply {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_REFRESH_TOKEN, refreshToken)
            putInt(KEY_USER_ID, userId)
            apply()
        }
    }

    /**
     * Возвращает сохраненный accessToken.
     */
    fun getAccessToken(): String? = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    /**
     * Возвращает сохраненный refreshToken.
     */
    fun getRefreshToken(): String? = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)

    /**
     * Возвращает сохраненный userId.
     */
    fun getUserId(): Int = sharedPreferences.getInt(KEY_USER_ID, -1)

    /**
     * Проверяет, сохранены ли токены и userId.
     */
    fun hasTokens(): Boolean {
        return getAccessToken() != null && getRefreshToken() != null && getUserId() != null
    }

    /**
     * Удаляет токены и userId (выход из системы).
     */
    fun clearTokens() {
        sharedPreferences.edit().apply {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_USER_ID)
            apply()
        }
    }
}
