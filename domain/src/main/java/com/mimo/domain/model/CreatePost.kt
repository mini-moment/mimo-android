package com.mimo.domain.model

data class CreatePost(
    val title: String? = null,
    val userId: Int? = null,
    val videoUrl: String? = null,
    val tagList: List<HashTag>? = emptyList(),
)
