package com.mimo.android.domain.repository

import com.mimo.android.domain.model.ApiResponse
import com.mimo.android.domain.model.HashTag
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    suspend fun getTags(): Flow<ApiResponse<List<HashTag>>>
}
