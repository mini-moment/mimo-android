package com.mimo.android.data.repository

import com.mimo.android.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface VideoRepository {
    fun uploadVideo(file: File): Flow<ApiResponse<String>>
}
