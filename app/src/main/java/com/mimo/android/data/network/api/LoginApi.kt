package com.mimo.android.data.network.api

import com.mimo.android.data.request.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("signUp")
    suspend fun signUp(
        @Body userRequest: UserRequest,
    ): Response<Boolean>
}
