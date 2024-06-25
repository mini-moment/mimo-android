package com.mimo.android.data.repository

import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.PostListResponse
import com.mimo.android.domain.model.PostData
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostRepository {

    suspend fun insertPost(
        postRequest: RequestBody,
        thumbnail: MultipartBody.Part,
    ): Flow<ApiResponse<String>>

    suspend fun getPostLists(
        ids: List<Int>
    ): Flow<ApiResponse<List<PostData>>>
}
