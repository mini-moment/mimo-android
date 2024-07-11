package com.mimo.presentation.video_detail

import androidx.activity.viewModels
import androidx.media3.exoplayer.ExoPlayer
import com.mimo.domain.model.Post
import com.mimo.presentation.R
import com.mimo.presentation.base.BaseActivity
import com.mimo.presentation.component.adapter.PostListAdapter
import com.mimo.presentation.databinding.ActivityVideoDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class VideoDetailActivity :
    BaseActivity<ActivityVideoDetailBinding>(R.layout.activity_video_detail) {
    private val videoDetailViewModel: VideoDetailViewModel by viewModels()
    private lateinit var postListAdapter: PostListAdapter
    private lateinit var exoPlayer: ExoPlayer

    override fun init() {
        initData()
        setPostListAdapter()
        with(binding) {
            viewModel = videoDetailViewModel
        }
    }

    private fun initData() {
        val postIndex = intent.getIntExtra("postIndex", -1)
        val postList: List<Post> = intent.getStringExtra("postList")?.let {
            Json.decodeFromString(it)
        } ?: emptyList()
        videoDetailViewModel.setPostList(postList)
    }

    private fun setPostListAdapter() {
        exoPlayer = ExoPlayer.Builder(this).build()
        with(binding.viewPagerVideodetailPostList) {
            postListAdapter = PostListAdapter(exoPlayer)
            adapter = postListAdapter
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}
