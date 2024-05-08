package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.TagsResponse
import com.mimo.android.data.network.api.TagApi
import retrofit2.Response
import javax.inject.Inject

class TagRemoteDataSourceImpl @Inject constructor(
    private val tagApi: TagApi,
) : TagRemoteDataSource {
    override suspend fun getTags(): Response<TagsResponse> {
        return tagApi.getTags()
    }
}
