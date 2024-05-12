package com.mimo.android.data.repository

import com.mimo.android.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface VideoRepository {

    fun uploadVideo(file: MultipartBody.Part): Flow<ApiResponse<String>>
}
