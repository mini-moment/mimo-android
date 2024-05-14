package com.mimo.android.presentation.video.upload

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.net.toUri
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
                uploadVideoViewModel.selectVideoUri(uri.toString())
            }
        }

    override fun initView() {
        with(binding) {
            btnFinishUpload.setOnClickListener {
                loadVideo()
            }
            btnUploadVideo.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.VideoOnly))
            }
            viewModel = uploadVideoViewModel
        }
        setRecyclerView()
        collectUiEvent()
    }

    private fun loadVideo() {
        uploadVideoViewModel.uiState.value.videoUri?.let { uri ->
            val filePath =
                getRealPathFromURI(requireContext(), uri.toUri())
            val file = File(filePath)
            val videoRequestBody =
                ProgressRequestBody(
                    file,
                    requireContext().getString(R.string.multipart_content_type).toMediaTypeOrNull(),
                ) { uploaded, total ->
                    val progress = (uploaded.toFloat() / total.toFloat() * 100).toInt()
                    viewLifecycleOwner.lifecycleScope.launch {
                        binding.progressUploadVideo.progress = progress
                        binding.tvProgress.text = "$progress%"
                    }
                }
            val videoFile =
                MultipartBody.Part.createFormData(
                    requireContext().getString(R.string.multipart_value),
                    file.name,
                    videoRequestBody,
                )
            uploadVideoViewModel.uploadVideo(videoFile)
        }
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

                        is UploadVideoEvent.VideoUploadSuccess -> {
                            uploadVideoViewModel.insertPost()
                        }

                        is UploadVideoEvent.PostUploadSuccess -> {
                            requireActivity().finish()
                        }
                    }
                }
            }
        }
    }
}
