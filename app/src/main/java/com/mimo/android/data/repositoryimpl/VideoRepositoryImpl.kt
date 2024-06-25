package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.remote.VideoRemoteDataSource
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.ErrorResponse
import com.mimo.android.data.model.response.apiHandler
import com.mimo.android.data.repository.VideoRepository
import com.mimo.android.data.util.MultiPartUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val videoRemoteDataSource: VideoRemoteDataSource) :
    VideoRepository {

    override fun uploadVideo(file: File): Flow<ApiResponse<String>> = flow {
        val videoFile = MultiPartUtil.convertToVideo(file)
        if (videoFile.body.contentLength() > MultiPartUtil.MAX_FILE_SIZE) {
            emit(ApiResponse.Failure)
            return@flow
        }
        val response = apiHandler {
            val result = videoRemoteDataSource.uploadVideo(videoFile)
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        when (response) {
            is ApiResponse.Success -> {
                emit(ApiResponse.Success(data = response.data.data ?: ""))
            }

            is ApiResponse.Error -> {
                emit(
                    ApiResponse.Error(
                        errorMessage = response.errorMessage,
                        errorCode = response.errorCode,
                    ),
                )
            }

            else -> {}
        }
    }
}
