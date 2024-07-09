package com.mimo.presentation.upload_video

interface UploadVideoEvent {

    data class VideoUploadSuccess(val videoPath: String) : UploadVideoEvent
    data object PostUploadSuccess : UploadVideoEvent

    data class ThumbnailsGetSuccess(val videoUrl: String) : UploadVideoEvent

    data class Error(
        val errorCode: Int = 0,
        val errorMessage: String = "",
    ) : UploadVideoEvent
}
