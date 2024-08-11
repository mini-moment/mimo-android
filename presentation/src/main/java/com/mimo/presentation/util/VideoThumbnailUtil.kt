package com.mimo.presentation.util

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import com.mimo.presentation.upload_video.VideoThumbnail
import java.util.Locale
import kotlin.math.abs

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
            val scaledBitmap =
                bitmap?.let {
                    Bitmap.createScaledBitmap(it, width / 10, it.height, false)
                }
            if (scaledBitmap != null) {
                thumbnails.add(VideoThumbnail(thumbnails.size, scaledBitmap))
            }
        }
        retriever.release()
        return thumbnails
    }

    fun getVideoThumbnail(
        start: Long,
        path: String,
    ): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        return retriever.getFrameAtTime(start, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    }
}

fun Long.converterTimeLine(): String {
    val absoluteMillis = abs(this)
    val posSeconds = (absoluteMillis / 1000) % 60
    val posMinutes = (absoluteMillis / (1000 * 60)) % 60
    val posHours = (absoluteMillis / (1000 * 60 * 60)) % 24

    return if (posHours > 0) {
        String.format(Locale.ROOT, "%02d:%02d:%02d", posHours, posMinutes, posSeconds)
    } else {
        String.format(Locale.ROOT, "%02d:%02d", posMinutes, posSeconds)
    }
}
