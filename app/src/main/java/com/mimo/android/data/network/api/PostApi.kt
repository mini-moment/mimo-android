package com.mimo.android.data.network.api

import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.InsertPostResponse
import com.mimo.android.domain.model.TagData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

interface PostApi {

    @POST("post/insert")
    suspend fun insertPost(
        @Body postRequest: InsertPostRequest,
    ): Response<InsertPostResponse>
}
