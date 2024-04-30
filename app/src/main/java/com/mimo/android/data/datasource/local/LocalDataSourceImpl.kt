package com.mimo.android.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mimo.android.BuildConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalDataSource {
    override suspend fun getAccessToken(): String = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN_KEY] ?: ""
    }.first()

    override suspend fun getRefreshToken(): String = dataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN_KEY] ?: ""
    }.first()

    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    override suspend fun deleteAccessToken() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }

    override suspend fun deleteRefreshToken() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey(BuildConfig.ACCESS_TOKEN_KEY)
        val REFRESH_TOKEN_KEY = stringPreferencesKey(BuildConfig.REFRESH_TOKEN_KEY)
    }
}
