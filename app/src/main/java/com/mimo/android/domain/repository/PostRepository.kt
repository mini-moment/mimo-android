package com.mimo.android.domain.repository

import com.mimo.android.domain.model.CreatePost
import com.mimo.android.domain.model.ApiResponse
import com.mimo.android.domain.model.Post
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

    suspend fun insertPost(
        postRequest: CreatePost,
        thumbnail: File,
        latitude: Double,
        longitude: Double,
    ): Flow<ApiResponse<String>>

    suspend fun getPostLists(
        ids: List<Int>,
    ): Flow<ApiResponse<List<Post>>>

    suspend fun getMyPost(): ApiResponse<List<Post>>
}
