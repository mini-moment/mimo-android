package com.mimo.android.data.repository

import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun insertPost(
        postRequest: InsertPostRequest,
    ): Flow<ApiResponse<String>>
}
