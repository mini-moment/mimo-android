package com.mimo.android.presentation.videodetail


import android.os.Build
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.mimo.android.R
import com.mimo.android.databinding.ActivityVideoDetailBinding
import com.mimo.android.domain.model.MarkerData
import com.mimo.android.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class VideoDetailActivity :
    BaseActivity<ActivityVideoDetailBinding>(R.layout.activity_video_detail) {
    private val playbackStateListener: Player.Listener = playbackStateListener()
    private var player: Player? = null
    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L

    override fun init() {
        initData()
        initializePlayer()
        with(binding) {
            tvVideodetailUsername.text = "UserName"
            tvVideodetailTimestamp.text = "1min"
            tvVideodetailLocation.text = "인동 에이바웃"
            btnVideodetailClose.setOnClickListener {

            }
        }
    }

    private fun initData() {
        val postList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayExtra("postList", MarkerData::class.java)
        } else {
            intent.getParcelableArrayExtra("postList")
        }
        val postIndex = intent.getIntExtra("postIndex", 0)
        Timber.d("포스트 전체 리스트 ${postList}")
        Timber.d("포스트 인덱스 ${postIndex}")
    }


    private fun initializePlayer() {    //player 연결
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.playerviewVideo.player = exoPlayer
                binding.playerviewVideo.useController = false

                val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4))
                exoPlayer.setMediaItems(listOf(mediaItem), mediaItemIndex, playbackPosition)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.addListener(playbackStateListener)
                exoPlayer.prepare()
            }
    }

    private fun releasePlayer() {   //player 연결 해제
        player?.let { player ->
            playbackPosition = player.currentPosition
            mediaItemIndex = player.currentMediaItemIndex
            playWhenReady = player.playWhenReady
            player.removeListener(playbackStateListener)
            player.release()
        }
        player = null
    }

    override fun onResume() {
        super.onResume()
        if (player == null) {
            initializePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

}

private fun playbackStateListener() = object : Player.Listener {
    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString: String = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
            else -> "UNKNOWN_STATE             -"
        }
    }
}