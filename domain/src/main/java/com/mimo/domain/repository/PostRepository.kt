package com.mimo.domain.repository

import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.CreatePost
import com.mimo.domain.model.Post
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
