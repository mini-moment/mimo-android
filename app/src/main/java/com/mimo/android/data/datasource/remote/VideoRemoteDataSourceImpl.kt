package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.UploadVideoResponse
import com.mimo.android.data.network.api.VideoApi
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class VideoRemoteDataSourceImpl @Inject constructor(private val videoApi: VideoApi) :
    VideoRemoteDataSource {
    override suspend fun uploadVideo(file: MultipartBody.Part): Response<UploadVideoResponse> {
        return videoApi.uploadVideo(file)
    }
}
