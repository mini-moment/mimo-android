package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.TagsResponse
import retrofit2.Response

interface TagRemoteDataSource {

    suspend fun getTags(): Response<TagsResponse>
}
