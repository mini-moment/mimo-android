package com.mimo.android.presentation.video_detail

interface VideoDetailEvent {
    data object PostsUploadSuccess : VideoDetailEvent

    data class Error(
        val errorCode: Int = 0,
        val errorMessage: String = "",
    ) : VideoDetailEvent
}
