package com.mimo.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.data.datasource.local.LocalDataSource
import com.mimo.data.datasource.remote.UserRemoteDataSource
import com.mimo.data.model.apiHandler
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
    override fun login(user: User): Flow<ApiResponse<Boolean>> = flow {
        val response = apiHandler {
            val result = userRemoteDataSource.login(user)
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

    override fun unRegister(): Flow<ApiResponse<Boolean>> = flow {
        val response = apiHandler {
            val result = userRemoteDataSource.unRegisterUser()
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        when (response) {
            is ApiResponse.Success -> {
                localDataSource.deleteAccessToken()
                localDataSource.deleteRefreshToken()
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
