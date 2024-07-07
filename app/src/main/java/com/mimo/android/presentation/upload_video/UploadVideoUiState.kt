package com.mimo.android.presentation.upload_video

import com.mimo.android.domain.model.HashTag

data class UploadVideoUiState(
    val videoUri: String = "",
    val tags: List<HashTag> = listOf(),
    val topic: String = "",
    val selectedTags: List<HashTag> = listOf(),
    val thumbnails: List<VideoThumbnail> = listOf(),
    val isThumbnailLoading: Boolean = false,
    val isPostUploadLoading: LoadingUiState = LoadingUiState.Init,
)

sealed class LoadingUiState {
    data object Init : LoadingUiState()

    data object Loading : LoadingUiState()

    data object Finish : LoadingUiState()
}
