package com.mimo.android.data.model.request

import com.mimo.android.domain.model.TagData

data class InsertPostRequest(
    val title: String? = null,
    val userId: Int? = null,
    val videoUrl: String? = null,
    val tagList: List<TagData>? = emptyList(),
)
