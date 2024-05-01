package com.mimo.android.data.network.api

import com.mimo.android.data.model.request.UserRequest
import com.mimo.android.data.model.response.UserSignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("signUp")
    suspend fun signUp(
        @Body userRequest: UserRequest,
    ): Response<UserSignUpResponse>
}
