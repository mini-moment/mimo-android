package com.mimo.android.core

import android.app.Application
import com.mimo.android.BuildConfig
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class MimoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET,
            "이재한",
        )
    }
}
