package com.mimo.android.data.repository

import com.mimo.android.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostRepository {

    suspend fun insertPost(
        postRequest: RequestBody,
        thumbnail: MultipartBody.Part,
    ): Flow<ApiResponse<String>>
}
