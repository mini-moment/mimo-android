package com.mimo.android.data.network.api

import com.mimo.android.data.model.response.InsertPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostApi {

    @Multipart
    @POST("post/insert")
    suspend fun insertPost(
        @Part("post") post: RequestBody,
        @Part thumbnail: MultipartBody.Part,
        @Part("latitude") latitude: Double,
        @Part("longitude") longitude: Double,
    ): Response<InsertPostResponse>
}
