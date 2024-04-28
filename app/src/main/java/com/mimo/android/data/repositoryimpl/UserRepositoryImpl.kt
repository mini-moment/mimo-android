package com.mimo.android.data.repositoryimpl

import com.mimo.android.data.network.api.LoginApi
import com.mimo.android.data.repository.UserRepository
import com.mimo.android.data.request.UserRequest
import com.mimo.android.data.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: LoginApi) : UserRepository {
    override fun signUp(userRequest: UserRequest): Flow<ApiResponse<Boolean>> = flow {
        val response = api.signUp(userRequest)
        runCatching {
            response
        }.onSuccess {
            if (it.isSuccessful) {
                emit(ApiResponse.Success(data = true))
            } else {
                emit(ApiResponse.Failure)
            }
        }.onFailure {
            emit(
                ApiResponse.Error(
                    errorCode = response.code(),
                    errorMessage = it.message ?: "",
                ),
            )
        }
    }
}
