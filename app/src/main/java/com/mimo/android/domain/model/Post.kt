package com.mimo.android.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val title: String,
    val userId: Int,
    val videoUrl: String,
    val tagList: List<Tag>,
    val profileImageUrl: String,
    val userName: String,
    val videoThumbnailUrl: String,
    val uploadTime: String,
) {

    @Serializable
    data class Tag(
        val id: Int,
        val name: String,
    )
}
