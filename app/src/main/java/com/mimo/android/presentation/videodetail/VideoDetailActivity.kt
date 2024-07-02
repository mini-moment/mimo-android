package com.mimo.android.presentation.videodetail


import android.os.Build
import androidx.activity.viewModels
import androidx.media3.exoplayer.ExoPlayer
import com.mimo.android.R
import com.mimo.android.databinding.ActivityVideoDetailBinding
import com.mimo.android.domain.model.PostData
import com.mimo.android.presentation.base.BaseActivity
import com.mimo.android.presentation.component.adapter.PostListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
        val postList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayExtra("postList", PostData::class.java)
        } else {
            intent.getParcelableArrayExtra("postList")
        }
        videoDetailViewModel.setPostList(postList?.toList() as List<PostData>)
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
