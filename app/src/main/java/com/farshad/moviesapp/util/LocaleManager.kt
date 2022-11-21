package com.farshad.moviesapp.util

import android.content.Context
import android.content.SharedPreferences
import com.farshad.moviesapp.util.Constants.LOCALE_CODE
import com.farshad.moviesapp.util.Constants.PREFS_LOCALE_FILE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocaleManager @Inject constructor(@ApplicationContext context: Context) {

    private val localePrefs: SharedPreferences = context.getSharedPreferences(PREFS_LOCALE_FILE, Context.MODE_PRIVATE)


    fun saveLocale(locale : String){
        localePrefs.edit().putString(LOCALE_CODE,locale).apply()
    }

    fun getLocale(): String? {
        return localePrefs.getString(LOCALE_CODE, "en")
    }

    fun clearSharedPref(){
        localePrefs.edit().clear().apply()
    }
}