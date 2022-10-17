package com.example.moviesapp.util

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.ViewModelAndRepository.dashboard.DashboardViewModel
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.model.ui.FilterByGenreInfo1
import com.example.moviesapp.model.ui.FilterByImdbInfo1

object Constants {

    const val BASE_URL = "https://moviesapi.ir/"

    const val USER_TOKEN = "user_token"
    const val USER_REFRESH_TOKEN = "user_refresh_token"
    const val PREFS_TOKEN_FILE = "prefs_token_file"
    const val IS_LOGGED_IN = "is_logged_in"

    //FOR NOTIFICATION
    const val CHANNEL_ID = "channel_Id"
    const val NOTIFICATION_ID = 1
    const val NOTIFICATION_ID_BIG_STYLE = 2


    //pref for locale
    const val PREFS_LOCALE_FILE = "prefs_locale_file"
    const val LOCALE_CODE = "locale_code"

    //pref for day night theme
    const val PREFS_THEME_FILE = "prefs_theme_file"
    const val THEME_CODE = "theme_code"



}