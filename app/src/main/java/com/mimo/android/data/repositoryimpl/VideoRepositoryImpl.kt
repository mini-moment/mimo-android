package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.remote.VideoRemoteDataSource
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.ErrorResponse
import com.mimo.android.data.model.response.apiHandler
import com.mimo.android.data.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val videoRemoteDataSource: VideoRemoteDataSource) :
    VideoRepository {
    override fun uploadVideo(file: MultipartBody.Part): Flow<ApiResponse<String>> = flow {
        val response = apiHandler {
            val result = videoRemoteDataSource.uploadVideo(file)
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
