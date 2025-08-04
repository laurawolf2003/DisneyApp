package com.example.disney.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DisneyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.tag("App").d("Application started") // Prüfen Sie Logcat
    }
}
