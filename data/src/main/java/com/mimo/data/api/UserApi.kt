package com.mimo.data.api

import com.mimo.data.model.UserLoginResponse
import com.mimo.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("login")
    suspend fun login(
        @Body user: User,
    ): Response<UserLoginResponse>
}
