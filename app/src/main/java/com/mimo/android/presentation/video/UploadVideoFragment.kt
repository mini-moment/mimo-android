package com.mimo.android.presentation.video

import com.mimo.android.R
import com.mimo.android.databinding.FragmentUploadVideoBinding
import com.mimo.android.presentation.base.BaseFragment

class UploadVideoFragment :
    BaseFragment<FragmentUploadVideoBinding>(R.layout.fragment_upload_video) {

    override fun initView() {
        with(binding) {
            btnFinishUpload.setOnClickListener {
                requireActivity().finish()
            }
        }
    }
}
