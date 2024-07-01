package com.mimo.android.presentation.video.upload

import android.annotation.SuppressLint
import android.location.Location
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import android.net.Uri
import android.os.Environment
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.slider.LabelFormatter
import com.mimo.android.R
import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.databinding.ActivityUploadVideoBinding
import com.mimo.android.domain.model.TagData
import com.mimo.android.presentation.base.BaseActivity
import com.mimo.android.presentation.component.adapter.TagClickListener
import com.mimo.android.presentation.component.adapter.TagListAdapter
import com.mimo.android.presentation.component.adapter.ThumbNailAdapter
import com.mimo.android.presentation.dialog.LoadingDialog
import com.mimo.android.presentation.util.ErrorMessage
import com.mimo.android.presentation.util.VideoThumbnailUtil
import com.mimo.android.presentation.util.convertBitmapToFile
import com.mimo.android.presentation.util.converterTimeLine
import com.mimo.android.presentation.util.getRealPathFromURI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.ByteBuffer

@AndroidEntryPoint
class UploadVideoActivity :
    BaseActivity<ActivityUploadVideoBinding>(R.layout.activity_upload_video) {

    private val uploadVideoViewModel: UploadVideoViewModel by viewModels()
    private val tagListAdapter = TagListAdapter()
    private val thumbNailAdapter = ThumbNailAdapter()
    private var player: Player? = null
    private var trackingJob: Job? = null
    private lateinit var video: File
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            val fileUrl = getRealPathFromURI(this, uri)
            val file = File(fileUrl)
            uploadVideoViewModel.setVideoUrl(uri.toString())
            val widthPixels = binding.recyclerViewVideoThumbnail.measuredWidth
            uploadVideoViewModel.getThumbnails(width = widthPixels, path = file.path)
        }
    }

    private fun playVideo(uri: Uri) {
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
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

    override fun init() {
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setRecyclerView()
        collectUiEvent()
        showLoadingDialog()
        sliderEventHandler()
        getLastLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        longitude = intent.getDoubleExtra("longitude", -1.0)
        latitude = intent.getDoubleExtra("latitude", -1.0)
        if (longitude == -1.0 || latitude == -1.0) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { success: Location? ->
                    success?.let { location ->
                        longitude = location.longitude
                        latitude = location.latitude
                    }
                }
                .addOnFailureListener {
                    showMessage(ErrorMessage.GPS_ERROR_MESSAGE)
                }
        }
    }

    private fun loadVideo() {
        val uri = uploadVideoViewModel.uiState.value.videoUri
        if (uri.isNotEmpty()) {
            val filePath = getRealPathFromURI(this, uri.toUri())
            val file = File(filePath)
            val videoLength = player?.duration ?: 0
            val newPosition1 = (videoLength * binding.sliderVideoThumbnail.values[0] / 100).toLong()
            val newPosition2 = (videoLength * binding.sliderVideoThumbnail.values[1] / 100).toLong()
            lifecycleScope.launch {
                val videoClip = withContext(Dispatchers.IO) {
                    editVideo(file, newPosition1, newPosition2)
                }
                uploadVideoViewModel.uploadVideo(videoClip)
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun editVideo(file: File, start: Long, end: Long): File {
        val rootPath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString()
        video = File(
            "$rootPath/" + file.name.replace(
                file.name,
                "edit_${file.name}",
            ),
        )
        val outputFilePath = video.absolutePath
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
        return video
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadVideoViewModel.event.collectLatest { uiEvent ->
                    when (uiEvent) {
                        is UploadVideoEvent.Error -> {
                            showMessage(uiEvent.errorMessage)
                        }

                        is UploadVideoEvent.VideoUploadSuccess -> {
                            video.delete()
                            val videoLength = player?.duration ?: 0
                            val start =
                                (videoLength * binding.sliderVideoThumbnail.values[0] / 100).toLong()
                            val uri = uploadVideoViewModel.uiState.value.videoUri
                            val filePath = getRealPathFromURI(this@UploadVideoActivity, uri.toUri())
                            val image = VideoThumbnailUtil().getVideoThumbnail(start, filePath)
                            image?.let { image ->
                                val thumbnailRequest = convertBitmapToFile(
                                    this@UploadVideoActivity,
                                    image,
                                )
                                val postRequest = InsertPostRequest(
                                    title = uploadVideoViewModel.uiState.value.topic,
                                    videoUrl = uiEvent.videoPath,
                                    tagList = uploadVideoViewModel.uiState.value.selectedTags,
                                )
                                uploadVideoViewModel.insertPost(
                                    postRequest,
                                    thumbnailRequest,
                                    latitude,
                                    longitude,
                                )
                            }
                        }

                        is UploadVideoEvent.PostUploadSuccess -> {
                            this@UploadVideoActivity.finish()
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
                                this@UploadVideoActivity.supportFragmentManager,
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

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
}
