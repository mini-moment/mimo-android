package com.mimo.android.presentation.video.upload

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
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
import com.mimo.android.presentation.util.getRealPathFromURI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File

@AndroidEntryPoint
class UploadVideoFragment :
    BaseFragment<FragmentUploadVideoBinding>(R.layout.fragment_upload_video) {

    private val uploadVideoViewModel: UploadVideoViewModel by viewModels()
    private val tagListAdapter = TagListAdapter()
    private val pickMedia =
        registerForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null) {
                val filePath = getRealPathFromURI(requireContext(), uri)
                val file = File(filePath)
                val videoRequestBody =
                    ProgressRequestBody(file, "video/*".toMediaTypeOrNull()) { uploaded, total ->
                        val progress = (uploaded.toFloat() / total.toFloat() * 100).toInt()
                        viewLifecycleOwner.lifecycleScope.launch {
                            binding.progressUploadVideo.progress = progress
                            binding.tvProgress.text = "$progress%"
                        }
                    }
                val videoFile =
                    MultipartBody.Part.createFormData("video", file.name, videoRequestBody)
                uploadVideoViewModel.uploadVideo(videoFile)
            }
        }

    override fun initView() {
        with(binding) {
            btnFinishUpload.setOnClickListener {
                uploadVideoViewModel.insertPost()
            }
            btnUploadVideo.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.VideoOnly))
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

                        is UploadVideoEvent.Success -> {
                            requireActivity().finish()
                        }
                    }
                }
            }
        }
    }
}
