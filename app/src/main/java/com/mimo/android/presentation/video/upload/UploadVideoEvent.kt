package com.mimo.android.presentation.video.upload

interface UploadVideoEvent {

    data object Success : UploadVideoEvent

    data class Error(
        val errorCode: Int = 0,
        val errorMessage: String = "",
    ) : UploadVideoEvent
}
