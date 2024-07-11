package com.mimo.data.api

import com.mimo.data.model.UploadVideoResponse
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
