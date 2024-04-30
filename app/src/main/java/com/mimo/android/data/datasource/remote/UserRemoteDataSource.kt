package com.mimo.android.data.datasource.remote

import com.mimo.android.data.request.UserRequest
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signUp(userRequest: UserRequest): Response<Boolean>
}
