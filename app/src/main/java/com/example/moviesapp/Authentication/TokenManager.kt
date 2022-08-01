package com.example.moviesapp.Authentication

import android.content.Context
import android.content.SharedPreferences
import com.example.moviesapp.model.network.UserAuthModel
import com.example.moviesapp.util.Constants.PREFS_TOKEN_FILE
import com.example.moviesapp.util.Constants.USER_REFRESH_TOKEN
import com.example.moviesapp.util.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: UserAuthModel?) {
        val editor = prefs.edit()

        token?.let {
            editor.putString(USER_TOKEN, token.access_token).apply()
            editor.putString(USER_REFRESH_TOKEN,token.refresh_token).apply()
        }
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}