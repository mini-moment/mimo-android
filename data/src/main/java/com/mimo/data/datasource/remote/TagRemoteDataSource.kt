package com.mimo.data.datasource.remote

import com.mimo.data.model.TagsResponse
import retrofit2.Response

interface TagRemoteDataSource {

    suspend fun getTags(): Response<TagsResponse>
}
