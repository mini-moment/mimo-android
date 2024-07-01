package com.mimo.android.presentation.mypage

import androidx.fragment.app.viewModels
import com.mimo.android.R
import com.mimo.android.databinding.FragmentMyPageBinding
import com.mimo.android.presentation.base.BaseFragment
import com.mimo.android.presentation.component.adapter.MyPostAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel : MyPageViewModel by viewModels()

    private lateinit var myPostAdapter: MyPostAdapter

    override fun initView() {
        initData()
        initAdapter()
        observeMyPost()
    }

    private fun initData(){
        myPageViewModel.getMyPost()
    }


    private fun initAdapter() {
        myPostAdapter = MyPostAdapter()
        binding.vpMyPost.adapter = myPostAdapter
    }

    private fun observeMyPost(){
        myPageViewModel.myPostList.observe(viewLifecycleOwner){
            myPostAdapter.submitList(it)
        }
    }
}
