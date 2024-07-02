package com.mimo.android.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimo.android.databinding.ItemPostBinding
import com.mimo.android.domain.model.PostData

class PostListAdapter(private val exoPlayer: ExoPlayer) : ListAdapter<PostData, PostItemViewHolder>(
    diffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostItemViewHolder(binding, exoPlayer)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: PostItemViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.startPlayer()
    }

    override fun onViewDetachedFromWindow(holder: PostItemViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopPlayer()
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PostData>() {
            override fun areItemsTheSame(
                oldItem: PostData,
                newItem: PostData,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PostData,
                newItem: PostData,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class PostItemViewHolder(
    private val binding: ItemPostBinding,
    private val exoPlayer: ExoPlayer
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var mediaItem: MediaItem

    fun bind(item: PostData) {
        with(binding) {
            post = item
            mediaItem = MediaItem.fromUri("http://192.168.0.8:8080/video/display/" + item.videoUrl)
        }
    }

    fun startPlayer() {
        binding.playerViewVideodetailPostVideo.player = exoPlayer
        binding.playerViewVideodetailPostVideo.useController = false
        exoPlayer?.repeatMode = REPEAT_MODE_ALL
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        exoPlayer?.playWhenReady = true
    }

    fun stopPlayer() {
        binding.playerViewVideodetailPostVideo.player = null
    }
}
