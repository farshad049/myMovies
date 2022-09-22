package com.example.moviesapp.util

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.ViewModelAndRepository.dashboard.DashboardViewModel
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.model.ui.FilterByGenreInfo1
import com.example.moviesapp.model.ui.FilterByImdbInfo1

object Constants {
    const val USER_TOKEN = "user_token"
    const val USER_REFRESH_TOKEN="user_refresh_token"
    const val PREFS_TOKEN_FILE = "prefs_token_file"
    const val BASE_URL = "https://moviesapi.ir/"
    const val IS_LOGGED_IN = "is_logged_in"

    //FOR NOTIFICATION
    val CHANNEL_ID = "channel_Id"
    val NOTIFICATION_ID = 1
    val NOTIFICATION_ID_BIG_STYLE = 2



}