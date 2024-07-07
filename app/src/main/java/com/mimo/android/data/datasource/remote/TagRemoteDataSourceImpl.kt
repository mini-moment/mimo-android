package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.TagsResponse
import com.mimo.android.data.api.TagApi
import retrofit2.Response
import javax.inject.Inject

class TagRemoteDataSourceImpl @Inject constructor(
    private val tagApi: TagApi,
) : TagRemoteDataSource {
    override suspend fun getTags(): Response<TagsResponse> {
        return tagApi.getTags()
    }
}
