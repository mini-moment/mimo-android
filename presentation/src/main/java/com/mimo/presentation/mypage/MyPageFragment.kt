package com.mimo.presentation.mypage

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mimo.presentation.BuildConfig
import com.mimo.presentation.R
import com.mimo.presentation.base.BaseFragment
import com.mimo.presentation.component.adapter.MyPostAdapter
import com.mimo.presentation.databinding.FragmentMyPageBinding
import com.mimo.presentation.video_detail.VideoDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
        setUserUnRegisterEvent()
        setInformationClickEvent()
    }

    private fun initData() {
        myPageViewModel.getMyPost()
    }

    private fun initAdapter() {
        myPostAdapter = MyPostAdapter()
        binding.vpMyPost.adapter = myPostAdapter
        binding.dotsIndicator.attachTo(binding.vpMyPost)
    }

    private fun observeMyPost() {
        myPageViewModel.myPostList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                myPostAdapter.submitList(it)
                binding.emptyVisible = it.isEmpty()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setInformationClickEvent() {
        binding.tvPrivacy.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVACY_WEB_URL))
            startActivity(intent)
        }
    }

    private fun setMyPostClickEvent() {
        myPostAdapter.setOnItemClickListener { index ->
            val myPostList = Json.encodeToString(myPageViewModel.myPostList.value)
            startActivity(
                Intent(
                    requireActivity(),
                    VideoDetailActivity::class.java,
                ).apply {
                    putExtra("postList", myPostList)
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

    private fun setUserUnRegisterEvent() {
        with(binding) {
            tvUnRegister.setOnClickListener {
                myPageViewModel.unRegisterUser()
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

                        is MyPageViewEvent.UnRegister -> {
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
