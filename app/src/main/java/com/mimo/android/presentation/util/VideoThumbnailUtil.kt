package com.mimo.android.presentation.util

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import com.mimo.android.domain.model.VideoThumbnail

class VideoThumbnailUtil {

    private val thumbnailCount = 10

    fun getVideoThumbnails(
        width: Int,
        path: String,
    ): List<VideoThumbnail> {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val duration = durationStr?.toLongOrNull() ?: return emptyList()
        val interval = duration / thumbnailCount
        val thumbnails = mutableListOf<VideoThumbnail>()
        for (i in 0 until thumbnailCount) {
            val timeUs = (i * interval) * 1000L
            val bitmap = retriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST)
            val scaledBitmap = bitmap?.let {
                Bitmap.createScaledBitmap(it, width / 10, it.height, false)
            }
            if (scaledBitmap != null) {
                thumbnails.add(VideoThumbnail(thumbnails.size, scaledBitmap))
            }
        }
        retriever.release()
        return thumbnails
    }
}

fun Long.converterTimeLine(): String {
    val posSeconds = (this / 1000) % 60
    val posMinutes = (this / (1000 * 60)) % 60
    return String.format("%02d:%02d", posMinutes, posSeconds)
}
