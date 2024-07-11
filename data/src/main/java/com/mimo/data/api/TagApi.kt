package com.mimo.data.api

import com.mimo.data.model.TagsResponse
import retrofit2.Response
import retrofit2.http.GET

interface TagApi {
    @GET("tags")
    suspend fun getTags(): Response<TagsResponse>
}
