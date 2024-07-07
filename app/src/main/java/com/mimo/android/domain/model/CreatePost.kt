package com.mimo.android.domain.model

data class CreatePost(
    val title: String? = null,
    val userId: Int? = null,
    val videoUrl: String? = null,
    val tagList: List<HashTag>? = emptyList(),
)
