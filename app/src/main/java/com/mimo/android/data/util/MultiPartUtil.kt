package com.mimo.android.data.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object MultiPartUtil {
    private const val VIDEO_CONTENT_TYPE = "video/*"
    private const val VIDEO_VALUE = "video"
    private const val IMAGE_CONTENT_TYPE = "image/jpeg"
    private const val IMAGE_VALUE = "thumbnail"
    const val MAX_FILE_SIZE = 60 * 1024 * 1024

    fun convertToVideo(video: File): MultipartBody.Part {
        val requestBody: RequestBody = video.asRequestBody(
            VIDEO_CONTENT_TYPE
                .toMediaTypeOrNull(),
        )
        return MultipartBody.Part.createFormData(
            VIDEO_VALUE,
            video.name,
            requestBody,
        )
    }

    fun convertToImage(thumbnail: File): MultipartBody.Part {
        val requestBody = thumbnail.asRequestBody(
            IMAGE_CONTENT_TYPE
                .toMediaTypeOrNull(),
        )
        return MultipartBody.Part.createFormData(
            IMAGE_VALUE,
            thumbnail.name,
            requestBody,
        )
    }
}
