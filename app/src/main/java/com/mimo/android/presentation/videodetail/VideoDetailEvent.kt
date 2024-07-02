package com.mimo.android.presentation.videodetail

interface VideoDetailEvent {
    data object PostsUploadSuccess : VideoDetailEvent

    data class Error(
        val errorCode: Int = 0,
        val errorMessage: String = "",
    ) : VideoDetailEvent
}
