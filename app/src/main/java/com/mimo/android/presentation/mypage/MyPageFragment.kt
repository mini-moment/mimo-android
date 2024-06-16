package com.mimo.android.presentation.mypage

import com.mimo.android.R
import com.mimo.android.databinding.FragmentMyPageBinding
import com.mimo.android.presentation.base.BaseFragment
import com.mimo.android.presentation.mypage.adapter.MyPostAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private lateinit var myPostAdapter: MyPostAdapter

    override fun initView() {
        initAdapter()
    }


    private fun initAdapter() {
        myPostAdapter = MyPostAdapter()
        binding.vpMyPost.adapter = myPostAdapter

    }
}
