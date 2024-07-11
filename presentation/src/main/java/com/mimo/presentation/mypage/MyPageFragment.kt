package com.mimo.presentation.mypage

import android.content.Intent
import androidx.fragment.app.viewModels
import com.mimo.presentation.R
import com.mimo.presentation.base.BaseFragment
import com.mimo.presentation.component.adapter.MyPostAdapter
import com.mimo.presentation.databinding.FragmentMyPageBinding
import com.mimo.presentation.video_detail.VideoDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    private lateinit var myPostAdapter: MyPostAdapter

    override fun initView() {
        initData()
        initAdapter()
        observeMyPost()
        setMyPostClickEvent()
    }

    private fun initData() {
        myPageViewModel.getMyPost()
    }

    private fun initAdapter() {
        myPostAdapter = MyPostAdapter()
        binding.vpMyPost.adapter = myPostAdapter
    }

    private fun observeMyPost() {
        myPageViewModel.myPostList.observe(viewLifecycleOwner) {
            myPostAdapter.submitList(it)
        }
    }

    private fun setMyPostClickEvent() {
        myPostAdapter.setOnItemClickListener { index ->
            startActivity(
                Intent(
                    requireActivity(),
                    VideoDetailActivity::class.java,
                ).apply {
                    putExtra("postList", myPageViewModel.myPostList.value?.toTypedArray())
                    putExtra("postIndex", index)
                },
            )
        }
    }
}
