package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.remote.PostRemoteDataSource
import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.ErrorResponse
import com.mimo.android.data.model.response.apiHandler
import com.mimo.android.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(val postRemoteDataSource: PostRemoteDataSource) :
    PostRepository {
    override suspend fun insertPost(
        postRequest: InsertPostRequest,
    ): Flow<ApiResponse<String>> = flow {
        val response = apiHandler {
            val result = postRemoteDataSource.insertPost(postRequest)
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
