package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.InsertPostResponse
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
}
