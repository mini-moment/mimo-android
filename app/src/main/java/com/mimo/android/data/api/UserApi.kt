package com.mimo.android.data.api

import com.mimo.android.data.model.UserSignUpResponse
import com.mimo.android.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("signUp")
    suspend fun signUp(
        @Body user: User,
    ): Response<UserSignUpResponse>
}
