package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.InsertPostResponse
import com.mimo.android.data.model.response.PostListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface PostRemoteDataSource {
    suspend fun insertPost(
        postRequest: RequestBody,
        thumbnail: MultipartBody.Part,
    ): Response<InsertPostResponse>

    suspend fun getPostList(
        ids : List<Int>
    ) : Response<PostListResponse>
}
