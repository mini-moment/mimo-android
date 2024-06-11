package com.mimo.android.presentation.video.upload

import android.annotation.SuppressLint
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import android.net.Uri
import android.os.Environment
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.slider.LabelFormatter
import com.mimo.android.R
import com.mimo.android.databinding.FragmentUploadVideoBinding
import com.mimo.android.domain.model.TagData
import com.mimo.android.presentation.base.BaseFragment
import com.mimo.android.presentation.dialog.LoadingDialog
import com.mimo.android.presentation.util.converterTimeLine
import com.mimo.android.presentation.util.getRealPathFromURI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.nio.ByteBuffer

@AndroidEntryPoint
class UploadVideoFragment :
    BaseFragment<FragmentUploadVideoBinding>(R.layout.fragment_upload_video) {

    private val uploadVideoViewModel: UploadVideoViewModel by viewModels()
    private val tagListAdapter = TagListAdapter()
    private val thumbNailAdapter = ThumbNailAdapter()
    private var player: Player? = null
    private var trackingJob: Job? = null

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            val fileUrl = getRealPathFromURI(requireContext(), uri)
            val file = File(fileUrl)
            uploadVideoViewModel.setVideoUrl(uri.toString())
            val widthPixels = binding.recyclerViewVideoThumbnail.measuredWidth
            uploadVideoViewModel.getThumbnails(width = widthPixels, path = file.path)
        }
    }

    private fun playVideo(uri: Uri) {
        player = ExoPlayer.Builder(requireActivity()).build().also { exoPlayer ->
            binding.playerViewVideo.player = exoPlayer
            exoPlayer.setMediaItem(MediaItem.fromUri(uri))
            exoPlayer.prepare()
            exoPlayer.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if (isPlaying) {
                        startTracking()
                    } else {
                        stopTracking()
                    }
                }
            })
        }
    }

    private fun startTracking() {
        trackingJob = lifecycleScope.launch {
            while (true) {
                trackingVideo()
                delay(100)
            }
        }
    }

    private fun stopTracking() {
        trackingJob?.cancel()
        trackingJob = null
    }

    private fun trackingVideo() {
        player?.let { player ->
            val currentPosition = player.currentPosition
            val duration = player.duration
            if (duration > 0) {
                val positionRatio = currentPosition.toFloat() / duration * 100
                binding.sliderVideoTime.value = positionRatio
            }
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
            sliderVideoThumbnail.setCustomThumbDrawable(R.drawable.clip)
        }
        setRecyclerView()
        collectUiEvent()
        showLoadingDialog()
        sliderEventHandler()
    }

    private fun loadVideo() {
        val uri = uploadVideoViewModel.uiState.value.videoUri
        if (uri.isNotEmpty()) {
            val filePath = getRealPathFromURI(requireContext(), uri.toUri())
            val file = File(filePath)
            val videoLength = player?.duration ?: 0
            val newPosition1 = (videoLength * binding.sliderVideoThumbnail.values[0] / 100).toLong()
            val newPosition2 = (videoLength * binding.sliderVideoThumbnail.values[1] / 100).toLong()
            lifecycleScope.launch {
                val editFile = withContext(Dispatchers.IO) {
                    editVideo(file, newPosition1, newPosition2)
                }
                val requestBody: RequestBody = editFile.asRequestBody(
                    requireContext().getString(R.string.multipart_content_type)
                        .toMediaTypeOrNull(),
                )
                val videoFile = MultipartBody.Part.createFormData(
                    requireContext().getString(R.string.multipart_value),
                    editFile.name,
                    requestBody,
                )
                uploadVideoViewModel.uploadVideo(videoFile)
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun editVideo(file: File, start: Long, end: Long): File {
        val rootPath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString()
        val tempFile = File(
            "$rootPath/" + file.name.replace(
                file.name,
                "edit_${file.name}",
            ),
        )
        val outputFilePath = tempFile.absolutePath
        val extractor = MediaExtractor()
        extractor.setDataSource(file.path)
        val muxer = MediaMuxer(outputFilePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        val trackIndexMap = IntArray(extractor.trackCount)
        for (i in trackIndexMap.indices) {
            val format = extractor.getTrackFormat(i)
            val mime = format.getString(MediaFormat.KEY_MIME)
            val trackIndex = muxer.addTrack(format)
            trackIndexMap[i] = trackIndex
        }
        muxer.start()
        val buffer = ByteBuffer.allocate(1024 * 1024)
        val bufferInfo = MediaCodec.BufferInfo()
        for (i in trackIndexMap.indices) {
            extractor.selectTrack(i)
            if (start != 0L) {
                extractor.seekTo(start * 1000L, MediaExtractor.SEEK_TO_CLOSEST_SYNC)
            }
            while (true) {
                bufferInfo.size = extractor.readSampleData(buffer, 0)
                bufferInfo.presentationTimeUs = extractor.sampleTime
                if (bufferInfo.size < 0) {
                    break
                }
                if (bufferInfo.presentationTimeUs > end * 1000L) {
                    break
                }
                bufferInfo.flags = extractor.sampleFlags
                muxer.writeSampleData(trackIndexMap[i], buffer, bufferInfo)
                extractor.advance()
            }
            extractor.unselectTrack(i)
        }
        extractor.release()
        muxer.stop()
        muxer.release()
        return tempFile
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
        with(binding.recyclerViewVideoThumbnail) {
            adapter = thumbNailAdapter
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
                            uploadVideoViewModel.insertPost(uiEvent.videoPath)
                        }

                        is UploadVideoEvent.PostUploadSuccess -> {
                            requireActivity().finish()
                        }

                        is UploadVideoEvent.ThumbnailsGetSuccess -> {
                            playVideo(uiEvent.videoUrl.toUri())
                        }
                    }
                }
            }
        }
    }

    private fun showLoadingDialog() {
        val dialog = LoadingDialog()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadVideoViewModel.uiState.collectLatest { uiState ->
                    when (uiState.isLoading) {
                        LoadingUiState.Finish -> {
                            dialog.dismiss()
                        }

                        LoadingUiState.Loading -> {
                            dialog.show(
                                requireActivity().supportFragmentManager,
                                "",
                            )
                        }

                        LoadingUiState.Init -> {}
                    }
                }
            }
        }
    }

    private fun sliderEventHandler() {
        videoThumbnailHandler()
        videoTimeHandler()
    }

    private fun videoThumbnailHandler() {
        with(binding) {
            sliderVideoThumbnail.addOnChangeListener { _, value, _ ->
                sliderVideoTime.value = value
                val videoLength = player?.duration ?: 0
                val newPosition = (videoLength * value / 100).toLong()
                player?.run {
                    seekTo(newPosition)
                    pause()
                }
            }
        }
    }

    private fun videoTimeHandler() {
        with(binding) {
            sliderVideoTime.addOnChangeListener { slider, value, fromUser ->
                val videoLength = player?.duration ?: 0
                val newPosition = (videoLength * value / 100).toLong()
                val minRangeValue = sliderVideoThumbnail.values[0]
                val maxRangeValue = sliderVideoThumbnail.values[1]
                if (fromUser) {
                    player?.run {
                        seekTo(newPosition)
                        pause()
                    }
                    if (value < minRangeValue) {
                        slider.value = minRangeValue
                        sliderVideoThumbnail.values = mutableListOf(value, maxRangeValue)
                    } else if (value > maxRangeValue) {
                        slider.value = maxRangeValue
                        sliderVideoThumbnail.values = mutableListOf(minRangeValue, value)
                    }
                } else {
                    if (value < minRangeValue) {
                        slider.value = minRangeValue
                    } else if (value >= maxRangeValue) {
                        player?.run {
                            val newPosition =
                                (videoLength * sliderVideoThumbnail.values[0] / 100).toLong()
                            seekTo(newPosition)
                            pause()
                        }
                    }
                }
                sliderVideoTime.setLabelFormatter {
                    newPosition.converterTimeLine()
                }
                sliderVideoTime.labelBehavior = LabelFormatter.LABEL_VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.let { player ->
            player.release()
        }
    }
}
