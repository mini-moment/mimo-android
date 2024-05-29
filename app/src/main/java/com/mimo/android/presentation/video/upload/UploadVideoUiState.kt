package com.mimo.android.presentation.video.upload

import com.mimo.android.domain.model.TagData

data class UploadVideoUiState(
    val videoUri: String = "",
    val tags: List<TagData> = listOf(),
    val topic: String = "",
    val selectedTags: List<TagData> = listOf(),
    val thumbnails: List<VideoThumbnail> = listOf(),
)
