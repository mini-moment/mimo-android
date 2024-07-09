package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.UserSignUpResponse
import com.mimo.domain.model.User
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signUp(user: User): Response<UserSignUpResponse>
}
