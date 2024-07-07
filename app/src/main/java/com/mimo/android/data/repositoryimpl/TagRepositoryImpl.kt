package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.remote.TagRemoteDataSource
import com.mimo.android.data.model.apiHandler
import com.mimo.android.domain.model.ApiResponse
import com.mimo.android.domain.model.ErrorResponse
import com.mimo.android.domain.model.HashTag
import com.mimo.android.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagRemoteDataSource: TagRemoteDataSource,
) :
    TagRepository {

    override suspend fun getTags(): Flow<ApiResponse<List<HashTag>>> = flow {
        val response = apiHandler {
            val result = tagRemoteDataSource.getTags()
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        when (response) {
            is ApiResponse.Success -> {
                emit(
                    ApiResponse.Success(
                        data = response.data.data ?: emptyList(),
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
