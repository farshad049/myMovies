package com.farshad.moviesapp.util

import android.content.Context
import android.content.SharedPreferences
import com.farshad.moviesapp.util.Constants.PREFS_THEME_FILE
import com.farshad.moviesapp.util.Constants.THEME_CODE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ThemeManager @Inject constructor(@ApplicationContext context: Context) {

    private val themePref: SharedPreferences = context.getSharedPreferences(PREFS_THEME_FILE, Context.MODE_PRIVATE)


    fun saveTheme(locale : String?){
        themePref.edit().putString(THEME_CODE,locale).apply()
    }

    fun getTheme(): String? {
        return themePref.getString(THEME_CODE, null)
    }

    fun clearSharedPref(){
        themePref.edit().clear().apply()
    }

}