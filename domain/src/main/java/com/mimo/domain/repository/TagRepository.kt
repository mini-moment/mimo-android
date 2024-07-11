package com.mimo.domain.repository

import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.HashTag
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    suspend fun getTags(): Flow<ApiResponse<List<HashTag>>>
}
