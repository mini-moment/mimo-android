package com.mimo.android.data.repositoryimpl

import com.mimo.android.data.datasource.local.LocalDataSource
import com.mimo.android.data.datasource.remote.UserRemoteDataSource
import com.mimo.android.data.repository.DataStoreRepository
import com.mimo.android.data.repository.UserRepository
import com.mimo.android.data.request.UserRequest
import com.mimo.android.data.response.ApiResponse
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
        }.onSuccess {
            if (it.isSuccessful) {
                dataStoreRepository.saveAccessToken(userRequest.accessToken)
                dataStoreRepository.saveRefreshToken(userRequest.refreshToken)
                emit(ApiResponse.Success(data = true))
            } else { // 네트워크 요청의 실패 다른 에러코드
                emit(
                    ApiResponse.Error(
                        errorCode = it.code(),
                        errorMessage = it.message(),
                    ),
                )
            }
        }.onFailure { // 다른 예외가 발생한 경우 -> 서버가 닫힌 경우
            emit(ApiResponse.Error(errorMessage = it.message ?: ""))
        }
    }
}
