package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.InsertPostResponse
import com.mimo.android.data.model.response.PostListResponse
import com.mimo.android.data.network.api.PostApi
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
