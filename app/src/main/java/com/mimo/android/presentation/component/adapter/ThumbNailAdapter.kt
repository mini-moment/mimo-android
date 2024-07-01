package com.mimo.android.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mimo.android.databinding.ItemVideoThumbnailBinding
import com.mimo.android.domain.model.VideoThumbnail

class ThumbNailAdapter :
    ListAdapter<VideoThumbnail, ThumbNailViewHolder>(DiffUtilCallback<VideoThumbnail>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbNailViewHolder {
        val binding =
            ItemVideoThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThumbNailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbNailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ThumbNailViewHolder(
    val binding: ItemVideoThumbnailBinding,
) : ViewHolder(binding.root) {

    fun bind(item: VideoThumbnail) {
        binding.ivThumbnail.setImageBitmap(item.bitmap)
    }
}
