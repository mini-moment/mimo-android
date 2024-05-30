package com.mimo.android.presentation.video

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import com.mimo.android.presentation.video.upload.VideoThumbnail

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
