package com.mimo.android.data.network.api

import com.mimo.android.data.model.response.UploadVideoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface VideoApi {

    @POST("video/upload")
    @Multipart
    suspend fun uploadVideo(
        @Part video: MultipartBody.Part,
    ): Response<UploadVideoResponse>
}
