package com.mimo.android.data.network.api

import com.mimo.android.data.model.response.TagsResponse
import retrofit2.Response
import retrofit2.http.GET

interface TagApi {

    @GET("tags")
    suspend fun getTags(): Response<TagsResponse>
}
