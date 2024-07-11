package com.mimo.data.api

import com.mimo.data.model.UserSignUpResponse
import com.mimo.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("signUp")
    suspend fun signUp(
        @Body user: User,
    ): Response<UserSignUpResponse>
}
