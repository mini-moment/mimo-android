package com.mimo.data.datasource.remote

import com.mimo.data.api.PostApi
import com.mimo.data.model.InsertPostResponse
import com.mimo.data.model.PostListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(private val postApi: PostApi) :
    PostRemoteDataSource {
    override suspend fun insertPost(
        postRequest: RequestBody,
        thumbnail: MultipartBody.Part,
        latitude: Double,
        longitude: Double,
    ): Response<InsertPostResponse> {
        return postApi.insertPost(postRequest, thumbnail, latitude, longitude)
    }

    override suspend fun getPostList(ids: List<Int>): Response<PostListResponse> {
        return postApi.getPostList(ids)
    }

    override suspend fun getMyPost(): Response<PostListResponse> {
        return postApi.getMyPost()
    }
}
