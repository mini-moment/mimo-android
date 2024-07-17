package com.mimo.data.datasource.remote

import com.mimo.data.model.UserLoginResponse
import com.mimo.domain.model.User
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun login(user: User): Response<UserLoginResponse>
}
