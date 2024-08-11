package com.mimo.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.data.datasource.remote.PostRemoteDataSource
import com.mimo.data.mapper.toPostList
import com.mimo.data.model.apiHandler
import com.mimo.data.util.MultiPartUtil
import com.mimo.data.util.NetworkContract
import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.CreatePost
import com.mimo.domain.model.ErrorResponse
import com.mimo.domain.model.Post
import com.mimo.domain.repository.PostRepository
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
        postRequest: CreatePost,
        thumbnail: File,
        latitude: Double,
        longitude: Double,
    ): Flow<ApiResponse<String>> = flow {
        val data = MultiPartUtil.convertToImage(thumbnail)
        val requestBody: RequestBody = Gson().toJson(postRequest)
            .toRequestBody(NetworkContract.ContentType.toMediaTypeOrNull())
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

    override suspend fun getPostLists(ids: List<Int>): Flow<ApiResponse<List<Post>>> = flow {
        val response = apiHandler {
            val result = postRemoteDataSource.getPostList(ids)
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        when (response) {
            is ApiResponse.Success -> {
                emit(
                    ApiResponse.Success(
                        response.data.toPostList(),
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

    override suspend fun getMyPost(): Flow<ApiResponse<List<Post>>> = flow {
        val response = apiHandler {
            val result = postRemoteDataSource.getMyPost()
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        when (response) {
            is ApiResponse.Success -> {
                emit(ApiResponse.Success(data = response.data.toPostList()))
            }

            is ApiResponse.Error -> {
                emit(
                    ApiResponse.Error(
                        errorCode = response.errorCode,
                        errorMessage = response.errorMessage,
                    )
                )
            }

            else -> {
                emit(ApiResponse.Failure)
            }
        }
    }
}
