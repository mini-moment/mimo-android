package com.mimo.android.presentation.video.upload

import com.mimo.android.domain.model.TagData

data class UploadVideoUiState(
    val videoUri: String? = null,
    val tags: List<TagData> = listOf(),
    val topic: String? = null,
    val selectedTags: List<TagData> = listOf(),
)
