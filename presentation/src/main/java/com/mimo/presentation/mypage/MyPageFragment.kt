package com.mimo.presentation.mypage

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mimo.presentation.R
import com.mimo.presentation.base.BaseFragment
import com.mimo.presentation.component.adapter.MyPostAdapter
import com.mimo.presentation.databinding.FragmentMyPageBinding
import com.mimo.presentation.video_detail.VideoDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    private lateinit var myPostAdapter: MyPostAdapter

    override fun initView() {
        initData()
        initAdapter()
        observeMyPost()
        setMyPostClickEvent()
        collectMyPageEvent()
        setUserLogOutEvent()
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

    private fun setUserLogOutEvent() {
        with(binding) {
            tvLogout.setOnClickListener {
                myPageViewModel.userLogout()
            }
        }
    }

    private fun collectMyPageEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myPageViewModel.event.collectLatest { event ->
                    when (event) {
                        is MyPageViewEvent.Logout -> {
                            requireActivity().finish()
                        }

                        is MyPageViewEvent.Error -> {
                            showMessage(event.errorMessage)
                        }
                    }
                }
            }
        }
    }
}
