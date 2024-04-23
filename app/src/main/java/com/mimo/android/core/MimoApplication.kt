package com.mimo.android.core

import android.app.Application
import timber.log.Timber

class MimoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
