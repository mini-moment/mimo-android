package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.request.UserRequest
import com.mimo.android.data.model.response.UserSignUpResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signUp(userRequest: UserRequest): Response<UserSignUpResponse>
}
