package com.farshad.moviesapp.util

import android.content.Context
import android.content.SharedPreferences
import com.farshad.moviesapp.util.Constants.IS_AUTHENTICATION_ENABLED
import com.farshad.moviesapp.util.Constants.PREFS_AUTHENTICATION_FILE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BiometricPrefManager @Inject constructor(@ApplicationContext context: Context) {

    private val biometricPrefs: SharedPreferences = context.getSharedPreferences(PREFS_AUTHENTICATION_FILE, Context.MODE_PRIVATE)

    fun saveBiometricStatus(biometric : Boolean){
        biometricPrefs.edit().putBoolean(IS_AUTHENTICATION_ENABLED , biometric).apply()
    }

    fun isBiometricLoginEnabled(): Boolean {
        return biometricPrefs.getBoolean(IS_AUTHENTICATION_ENABLED,false)
    }
}