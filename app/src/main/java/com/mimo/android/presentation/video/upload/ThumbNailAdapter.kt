package com.mimo.android.presentation.video.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mimo.android.databinding.ItemVideoThumbnailBinding
import com.mimo.android.domain.model.VideoThumbnail

class ThumbNailAdapter : ListAdapter<VideoThumbnail, ThumbNailViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbNailViewHolder {
        val binding =
            ItemVideoThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThumbNailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbNailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<VideoThumbnail>() {
            override fun areItemsTheSame(
                oldItem: VideoThumbnail,
                newItem: VideoThumbnail,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: VideoThumbnail,
                newItem: VideoThumbnail,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ThumbNailViewHolder(
    val binding: ItemVideoThumbnailBinding,
) : ViewHolder(binding.root) {

    fun bind(item: VideoThumbnail) {
        binding.ivThumbnail.setImageBitmap(item.bitmap)
    }
}
