package com.farshad.moviesapp.Authentication

import android.content.Context
import android.content.SharedPreferences
import com.farshad.moviesapp.model.network.UserAuthModel
import com.farshad.moviesapp.util.Constants.IS_LOGGED_IN
import com.farshad.moviesapp.util.Constants.PREFS_TOKEN_FILE
import com.farshad.moviesapp.util.Constants.USER_REFRESH_TOKEN
import com.farshad.moviesapp.util.Constants.USER_TOKEN
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
            editor.putBoolean(IS_LOGGED_IN,true).apply ()
        }
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return prefs.getString(USER_REFRESH_TOKEN, null)
    }

    fun getIsLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN,false)
    }

    fun clearSharedPref(){
        val editor = prefs.edit()
        editor.clear().apply()

    }
}