package com.mimo.android.data.model.response

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
        data class Tag(
            val id: Int,
            val name: String,
        )

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
