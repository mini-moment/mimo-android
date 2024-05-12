package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.InsertPostResponse
import com.mimo.android.domain.model.TagData
import retrofit2.Response

interface PostRemoteDataSource {
    suspend fun insertPost(
        postRequest: InsertPostRequest,
    ): Response<InsertPostResponse>
}
