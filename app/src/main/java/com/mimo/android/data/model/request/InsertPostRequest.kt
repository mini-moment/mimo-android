package com.mimo.android.data.model.request

import com.mimo.android.domain.model.TagData

data class InsertPostRequest(
    val title: String? = null,
    val userId: Int? = null,
    val videoUrl: String? = null,
    val regionId: Int? = 0,
    val latitude: Long? = 0L,
    val longitude: Long? = 0L,
    val tagList: List<TagData>? = emptyList(),
)
