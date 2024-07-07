package com.mimo.android.data.api

import com.mimo.android.data.model.TagsResponse
import retrofit2.Response
import retrofit2.http.GET

interface TagApi {
    @GET("tags")
    suspend fun getTags(): Response<TagsResponse>
}
