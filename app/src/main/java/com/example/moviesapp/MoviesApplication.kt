package com.example.moviesapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication:Application() {


    override fun onCreate() {
        super.onCreate()
    }
}