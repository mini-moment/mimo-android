package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.local.LocalDataSource
import com.mimo.android.data.datasource.remote.UserRemoteDataSource
import com.mimo.android.data.model.apiHandler
import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.ErrorResponse
import com.mimo.domain.model.User
import com.mimo.domain.repository.UserRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localDataSource: LocalDataSource,
) : UserRepository {
    override fun signUp(user: User): Flow<ApiResponse<Boolean>> = flow {
        val response = apiHandler {
            val result = userRemoteDataSource.signUp(user)
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        when (response) {
            is ApiResponse.Success -> {
                localDataSource.saveAccessToken(user.accessToken ?: "")
                localDataSource.saveRefreshToken(user.refreshToken ?: "")
                emit(ApiResponse.Success(data = response.data.data ?: false))
            }

            is ApiResponse.Error -> {
                emit(
                    ApiResponse.Error(
                        errorCode = response.errorCode,
                        errorMessage = response.errorMessage,
                    ),
                )
            }

            else -> {}
        }
    }
}
