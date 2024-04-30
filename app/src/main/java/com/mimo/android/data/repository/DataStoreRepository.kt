package com.mimo.android.data.repository

import com.mimo.android.data.response.ApiResponse
import com.mimo.android.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun getUserToken(): Flow<ApiResponse<LoginResponse>>

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun deleteAccessToken()

    suspend fun deleteRefreshToken()
}
