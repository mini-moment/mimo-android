package com.mimo.android.data.repositoryimpl

import com.mimo.android.data.datasource.local.LocalDataSource
import com.mimo.android.domain.repository.DataStoreRepository
import com.mimo.android.domain.model.ApiResponse
import com.mimo.android.domain.model.User
import com.mimo.android.presentation.util.ErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : DataStoreRepository {
    override suspend fun getUserToken(): Flow<ApiResponse<User>> = flow {
        val accessToken = localDataSource.getAccessToken()
        val refreshToken = localDataSource.getRefreshToken()
        if (accessToken.isBlank().not() && refreshToken.isBlank().not()) {
            emit(
                ApiResponse.Success(
                    User(
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                    ),
                ),
            )
        } else if (accessToken.isBlank()) {
            emit(
                ApiResponse.Error(errorMessage = ErrorMessage.NO_ACCESS_TOKEN_MESSAGE),
            )
        } else {
            emit(
                ApiResponse.Error(errorMessage = ErrorMessage.NO_REFRESH_TOKEN_MESSAGE),
            )
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        localDataSource.saveAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        localDataSource.saveRefreshToken(refreshToken)
    }

    override suspend fun deleteAccessToken() {
        localDataSource.deleteAccessToken()
    }

    override suspend fun deleteRefreshToken() {
        localDataSource.deleteRefreshToken()
    }
}
