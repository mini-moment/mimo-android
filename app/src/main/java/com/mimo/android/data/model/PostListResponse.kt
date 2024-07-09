package com.mimo.android.data.model

import com.mimo.domain.model.Post.Tag

data class PostListResponse(
    val data: List<Data>,
) {
    data class Data(
        val id: Int,
        val tagList: List<Tag>,
        val title: String,
        val userInfo: UserInfo,
        val videoThumbnailUrl: String,
        val videoUrl: String,
        val uploadTime: String,
    ) {
        data class UserInfo(
            val accessToken: String,
            val id: Int,
            val profileImageUrl: String,
            val refreshToken: String,
            val userContact: String,
            val userName: String,
        )
    }
}
