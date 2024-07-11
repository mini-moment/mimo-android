package com.mimo.data.datasource.remote

import com.mimo.data.api.VideoApi
import com.mimo.data.model.UploadVideoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class VideoRemoteDataSourceImpl @Inject constructor(private val videoApi: VideoApi) :
    VideoRemoteDataSource {
    override suspend fun uploadVideo(file: MultipartBody.Part): Response<UploadVideoResponse> {
        return videoApi.uploadVideo(file)
    }
}
