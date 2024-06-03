package com.mimo.android.presentation.video.upload

interface UploadVideoEvent {

    data object VideoUploadSuccess : UploadVideoEvent
    data object PostUploadSuccess : UploadVideoEvent

    data class ThumbnailsGetSuccess(val videoUrl: String) : UploadVideoEvent

    data class Error(
        val errorCode: Int = 0,
        val errorMessage: String = "",
    ) : UploadVideoEvent
}
