package com.mimo.android.data.model.response

data class PostListResponse(
    val data: List<Data>
) {
    data class Data(
        val id: Int,
        val markerId: Int,
        val tagList: List<Tag>,
        val title: String,
        val userId: Int,
        val userInfo: UserInfo,
        val videoThumbnailUrl: String,
        val videoUrl: String
    ) {
        data class Tag(
            val id: Int,
            val name: String
        )

        data class UserInfo(
            val accessToken: String,
            val id: Int,
            val profileImageUrl: String,
            val refreshToken: String,
            val userContact: String,
            val userName: String
        )
    }
}