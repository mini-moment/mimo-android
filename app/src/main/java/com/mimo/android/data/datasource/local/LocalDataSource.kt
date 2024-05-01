package com.mimo.android.data.datasource.local

interface LocalDataSource {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
}
