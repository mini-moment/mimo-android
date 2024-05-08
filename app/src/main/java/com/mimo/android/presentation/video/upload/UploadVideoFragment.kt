package com.mimo.android.presentation.video.upload

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mimo.android.R
import com.mimo.android.databinding.FragmentUploadVideoBinding
import com.mimo.android.domain.model.TagData
import com.mimo.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UploadVideoFragment :
    BaseFragment<FragmentUploadVideoBinding>(R.layout.fragment_upload_video) {

    private val uploadVideoViewModel: UploadVideoViewModel by viewModels()
    private val tagListAdapter = TagListAdapter()

    override fun initView() {
        with(binding) {
            btnFinishUpload.setOnClickListener {
                requireActivity().finish()
            }
            viewModel = uploadVideoViewModel
        }
        setRecyclerView()
        collectUiEvent()
    }

    private fun setRecyclerView() {
        with(binding.recyclerViewTag) {
            tagListAdapter.setTagClickListener(object : TagClickListener {
                override fun onClick(item: TagData) {
                    uploadVideoViewModel.selectTag()
                }
            })
            adapter = tagListAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
        }
    }

    private fun collectUiEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadVideoViewModel.event.collectLatest { uiEvent ->
                    when (uiEvent) {
                        is UploadVideoEvent.Error -> {
                            showMessage(uiEvent.errorMessage)
                        }
                    }
                }
            }
        }
    }
}
