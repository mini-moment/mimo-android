package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.remote.PostRemoteDataSource
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.ErrorResponse
import com.mimo.android.data.model.response.apiHandler
import com.mimo.android.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(val postRemoteDataSource: PostRemoteDataSource) :
    PostRepository {
    override suspend fun insertPost(
        postRequest: RequestBody,
        thumbnail: MultipartBody.Part,
    ): Flow<ApiResponse<String>> = flow {
        val response = apiHandler {
            val result = postRemoteDataSource.insertPost(postRequest, thumbnail)
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        when (response) {
            is ApiResponse.Success -> {
                emit(
                    ApiResponse.Success(
                        data = response.data.data ?: "",
                    ),
                )
            }

            is ApiResponse.Error -> {
                emit(
                    ApiResponse.Error(
                        errorCode = response.errorCode,
                        errorMessage = response.errorMessage,
                    ),
                )
            }

            else -> {}
        }
    }
}
