package com.mimo.android.data.datasource.remote

import com.mimo.android.data.network.api.UserApi
import com.mimo.android.data.model.request.UserRequest
import com.mimo.android.data.model.response.UserSignUpResponse
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : UserRemoteDataSource {
    override suspend fun signUp(userRequest: UserRequest): Response<UserSignUpResponse> {
        return userApi.signUp(userRequest)
    }
}
