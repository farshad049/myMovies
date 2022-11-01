package com.farshad.moviesapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication:Application() {
    companion object {
        lateinit var context: Context
    }


    override fun onCreate() {
        super.onCreate()
        context = this
    }
}