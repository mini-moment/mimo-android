package com.mimo.android.domain.repository

import com.mimo.android.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface VideoRepository {
    fun uploadVideo(file: File): Flow<ApiResponse<String>>
}
