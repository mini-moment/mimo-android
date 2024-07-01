package com.mimo.android.data.repository

import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.PostListResponse
import com.mimo.android.domain.model.PostData
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

    suspend fun insertPost(
        postRequest: InsertPostRequest,
        thumbnail: File,
        latitude: Double,
        longitude: Double,
    ): Flow<ApiResponse<String>>

    suspend fun getPostLists(
        ids: List<Int>
    ): Flow<ApiResponse<List<PostData>>>

    suspend fun getMyPost() : ApiResponse<List<PostData>>
}
