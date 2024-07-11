package com.mimo.data.datasource.remote

import com.mimo.data.model.InsertPostResponse
import com.mimo.data.model.PostListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface PostRemoteDataSource {
    suspend fun insertPost(
        postRequest: RequestBody,
        thumbnail: MultipartBody.Part,
        latitude: Double,
        longitude: Double,
    ): Response<InsertPostResponse>

    suspend fun getPostList(
        ids: List<Int>,
    ): Response<PostListResponse>

    suspend fun getMyPost(): Response<PostListResponse>
}
