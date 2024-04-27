package com.mimo.android.data.repository

import com.mimo.android.data.response.ApiResponse
import com.mimo.android.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun getAccessToken(): Flow<ApiResponse<LoginResponse>>

    suspend fun saveAccessToken(loginSuccess: LoginResponse)

    suspend fun deleteAccessToken()


}
