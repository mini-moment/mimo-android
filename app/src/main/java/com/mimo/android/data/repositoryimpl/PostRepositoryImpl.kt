package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.remote.PostRemoteDataSource
import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.ErrorResponse
import com.mimo.android.data.model.response.apiHandler
import com.mimo.android.data.repository.PostRepository
import com.mimo.android.data.util.MultiPartUtil
import com.mimo.android.domain.model.PostData
import com.mimo.android.domain.model.toPostData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postRemoteDataSource: PostRemoteDataSource) :
    PostRepository {

    override suspend fun insertPost(
        postRequest: InsertPostRequest,
        thumbnail: File,
        latitude: Double,
        longitude: Double,
    ): Flow<ApiResponse<String>> = flow {
        val data = MultiPartUtil.convertToImage(thumbnail)
        val requestBody: RequestBody = Gson().toJson(postRequest)
            .toRequestBody("application/json".toMediaTypeOrNull())
        val response = apiHandler {
            val result = postRemoteDataSource.insertPost(requestBody, data, latitude, longitude)
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

    override suspend fun getPostLists(ids: List<Int>): Flow<ApiResponse<List<PostData>>> = flow {
        val response = apiHandler {
            val result = postRemoteDataSource.getPostList(ids)
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        when (response) {
            is ApiResponse.Success -> {
                emit(
                    ApiResponse.Success(
                        response.data.toPostData(),
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
