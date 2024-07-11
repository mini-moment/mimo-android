package com.mimo.domain.repository

import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun getUserToken(): Flow<ApiResponse<User>>

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun deleteAccessToken()

    suspend fun deleteRefreshToken()
}
