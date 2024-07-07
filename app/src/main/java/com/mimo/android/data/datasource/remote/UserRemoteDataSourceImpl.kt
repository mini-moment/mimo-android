package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.UserSignUpResponse
import com.mimo.android.data.api.UserApi
import com.mimo.android.domain.model.User
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : UserRemoteDataSource {
    override suspend fun signUp(user: User): Response<UserSignUpResponse> {
        return userApi.signUp(user)
    }
}
