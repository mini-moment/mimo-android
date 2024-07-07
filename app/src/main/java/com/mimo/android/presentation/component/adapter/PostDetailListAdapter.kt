package com.mimo.android.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimo.android.BuildConfig
import com.mimo.android.databinding.ItemPostBinding
import com.mimo.android.domain.model.Post

class PostListAdapter(private val exoPlayer: ExoPlayer) : ListAdapter<Post, PostItemViewHolder>(
    DiffUtilCallback()
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
}

class PostItemViewHolder(
    private val binding: ItemPostBinding,
    private val exoPlayer: ExoPlayer
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var mediaItem: MediaItem

    fun bind(item: Post) {
        with(binding) {
            post = item
            mediaItem = MediaItem.fromUri(BuildConfig.MIMO_SERVER_URL + BuildConfig.MIMO_VIDEO_BASE_URL + item.videoUrl)
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
