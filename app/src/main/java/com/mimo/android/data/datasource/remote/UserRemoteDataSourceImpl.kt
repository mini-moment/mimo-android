package com.mimo.android.data.datasource.remote

import com.mimo.android.data.network.api.UserApi
import com.mimo.android.data.request.UserRequest
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : UserRemoteDataSource {
    override suspend fun signUp(userRequest: UserRequest): Response<Boolean> {
        return userApi.signUp(userRequest)
    }
}
