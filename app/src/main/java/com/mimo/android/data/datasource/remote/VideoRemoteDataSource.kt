package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.UploadVideoResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface VideoRemoteDataSource {

    suspend fun uploadVideo(file: MultipartBody.Part): Response<UploadVideoResponse>
}
