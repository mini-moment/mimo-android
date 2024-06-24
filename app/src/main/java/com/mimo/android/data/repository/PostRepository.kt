package com.mimo.android.data.repository

import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

    suspend fun insertPost(
        postRequest: InsertPostRequest,
        thumbnail: File,
        latitude: Double,
        longitude: Double,
    ): Flow<ApiResponse<String>>
}
