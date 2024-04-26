package com.mimo.android.core

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mimo.android.BuildConfig
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.prefs.Preferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BuildConfig.DATASTORE_NAME)


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
