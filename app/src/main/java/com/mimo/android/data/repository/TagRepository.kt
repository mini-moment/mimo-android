package com.mimo.android.data.repository

import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.domain.model.TagData
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    suspend fun getTags(): Flow<ApiResponse<List<TagData>>>
}
