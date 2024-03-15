package com.example.intouchmobileapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import ua.naiksoftware.stomp.BuildConfig

@HiltAndroidApp
class InTouchApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}