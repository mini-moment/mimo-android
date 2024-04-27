package com.mimo.android.data.repositoryimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mimo.android.BuildConfig
import com.mimo.android.data.repository.DataStoreRepository
import com.mimo.android.data.response.ApiResponse
import com.mimo.android.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    override suspend fun getAccessToken(): Flow<ApiResponse<LoginResponse>> =
        dataStore.data.map { prefs ->
            if (prefs[ACCESS_TOKEN_KEY] == null) {
                ApiResponse.Failure
            } else {
                ApiResponse.Success(
                    LoginResponse(
                        accessToken = prefs[ACCESS_TOKEN_KEY] ?: "",
                        refreshToken = prefs[REFRESH_TOKEN_KEY] ?: "",
                    ),
                )
            }
        }.catch { exception ->
            ApiResponse.Error(
                errorMessage = exception.message ?: "",
            )
        }

    override suspend fun saveAccessToken(loginSuccess: LoginResponse) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = loginSuccess.accessToken
            prefs[REFRESH_TOKEN_KEY] = loginSuccess.refreshToken
        }
    }

    override suspend fun deleteAccessToken() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey(BuildConfig.ACCESS_TOKEN_KEY)
        val REFRESH_TOKEN_KEY = stringPreferencesKey(BuildConfig.REFRESH_TOKEN_KEY)
    }
}