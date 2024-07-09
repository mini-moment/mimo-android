package com.mimo.data.datasource.remote

import com.mimo.data.api.TagApi
import com.mimo.data.model.TagsResponse
import retrofit2.Response
import javax.inject.Inject

class TagRemoteDataSourceImpl @Inject constructor(
    private val tagApi: TagApi,
) : TagRemoteDataSource {
    override suspend fun getTags(): Response<TagsResponse> {
        return tagApi.getTags()
    }
}
