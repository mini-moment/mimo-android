package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.InsertPostResponse
import com.mimo.android.data.network.api.PostApi
import com.mimo.android.domain.model.TagData
import retrofit2.Response
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(private val postApi: PostApi) :
    PostRemoteDataSource {
    override suspend fun insertPost(
        postRequest: InsertPostRequest,
    ): Response<InsertPostResponse> {
        return postApi.insertPost(postRequest)
    }
}
