package com.example.myapplication.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

object AccessTokenValidator {

    private const val ACCESS_TOKEN = "access_token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val ACCESS_TOKEN_EXPIRY = "access_token_expiry"
    private const val REFRESH_TOKEN_EXPIRY = "refresh_token_expiry"

    fun saveUserCredentials(context: Context, accessToken: String, refreshToken: String, accessTokenExpiry: Long, refreshTokenExpiry: Long) {
        val sharedPreferences = getSharedPreferences(context)
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
            putLong(ACCESS_TOKEN_EXPIRY, accessTokenExpiry)
            putLong(REFRESH_TOKEN_EXPIRY, refreshTokenExpiry)
        }
    }

    fun clearUserCredentials(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        sharedPreferences.edit {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(ACCESS_TOKEN_EXPIRY)
            remove(REFRESH_TOKEN_EXPIRY)
        }
    }

    fun getAccessToken(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }

    suspend fun isValidToken(context: Context): Boolean {
        return withContext(Dispatchers.IO) {
            val accessTokenExpiry = getAccessTokenExpiry(context)
            accessTokenExpiry > Calendar.getInstance().timeInMillis
        }
    }

    private fun getAccessTokenExpiry(context: Context): Long {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getLong(ACCESS_TOKEN_EXPIRY, 0)
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("access_tokens", Context.MODE_PRIVATE)
    }

    fun isAccessTokenAvailable(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
        return !accessToken.isNullOrEmpty()
    }

}
