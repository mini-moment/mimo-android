package com.mimo.android.presentation.video.upload

import com.mimo.android.domain.model.TagData

data class UploadVideoUiState(
    val videoUri: String = "",
    val tags: List<TagData> = listOf(),
    val topic: String = "",
    val selectedTags: List<TagData> = listOf(),
    val thumbnails: List<VideoThumbnail> = listOf(),
    val isLoading: LoadingUiState = LoadingUiState.Init,
)

sealed class LoadingUiState {
    data object Init : LoadingUiState()

    data object Loading : LoadingUiState()

    data object Finish : LoadingUiState()
}
