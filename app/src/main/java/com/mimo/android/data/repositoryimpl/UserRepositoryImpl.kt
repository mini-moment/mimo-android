package com.mimo.android.data.repositoryimpl

import com.mimo.android.data.datasource.remote.UserRemoteDataSource
import com.mimo.android.data.repository.DataStoreRepository
import com.mimo.android.data.repository.UserRepository
import com.mimo.android.data.model.request.UserRequest
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.util.ErrorCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val dataStoreRepository: DataStoreRepository,
) : UserRepository {
    override fun signUp(userRequest: UserRequest): Flow<ApiResponse<Boolean>> = flow {
        runCatching {
            userRemoteDataSource.signUp(userRequest)
        }.onSuccess { response ->
            response.body()?.let {
                it.data?.let {
                    dataStoreRepository.saveAccessToken(userRequest.accessToken)
                    dataStoreRepository.saveRefreshToken(userRequest.refreshToken)
                    emit(ApiResponse.Success(data = it))
                } ?: run {
                    emit(
                        ApiResponse.Error(
                            errorCode = it.statusCode ?: ErrorCode.NONE,
                            errorMessage = it.message ?: "",
                        ),
                    )
                }
            }
        }.onFailure { // 다른 예외가 발생한 경우 -> 서버가 닫힌 경우
            emit(ApiResponse.Error(errorMessage = it.message ?: ""))
        }
    }
}
