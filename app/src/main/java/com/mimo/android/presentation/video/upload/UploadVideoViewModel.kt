package com.mimo.android.presentation.video.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.android.data.model.request.InsertPostRequest
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.repository.PostRepository
import com.mimo.android.data.repository.TagRepository
import com.mimo.android.data.repository.VideoRepository
import com.mimo.android.presentation.util.ErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadVideoViewModel @Inject constructor(
    private val tagRepository: TagRepository,
    private val videoRepository: VideoRepository,
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<UploadVideoEvent>()
    val event: SharedFlow<UploadVideoEvent> = _event
    private val _uiState = MutableStateFlow(UploadVideoUiState())
    val uiState: StateFlow<UploadVideoUiState> = _uiState

    init {
        loadTags()
    }

    private fun loadTags() {
        viewModelScope.launch {
            tagRepository.getTags().collectLatest { tagResponse ->
                when (tagResponse) {
                    is ApiResponse.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                tags = tagResponse.data,
                            )
                        }
                    }

                    is ApiResponse.Error -> {
                        _event.emit(
                            UploadVideoEvent.Error(
                                errorCode = tagResponse.errorCode,
                                errorMessage = tagResponse.errorMessage,
                            ),
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    fun selectTag() {
        viewModelScope.launch {
            _uiState.update { uiState ->
                val newTags = uiState.tags.toMutableList().apply {
                    addAll(uiState.selectedTags.filter { !it.isSelected })
                }.filter { !it.isSelected }

                val newSelectedTags = uiState.selectedTags.toMutableList().apply {
                    addAll(uiState.tags.filter { it.isSelected })
                }.filter { it.isSelected }
                if (newSelectedTags.size >= 7) {
                    _event.emit(
                        UploadVideoEvent.Error(
                            errorMessage = ErrorMessage.ENOUGH_HASH_TAG_MESSAGE,
                        ),
                    )
                    return@launch
                }
                uiState.copy(
                    tags = newTags,
                    selectedTags = newSelectedTags,
                )
            }
        }
    }

    fun uploadVideo(file: MultipartBody.Part) {
        viewModelScope.launch {
            videoRepository.uploadVideo(file).collectLatest { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                videoUri = response.data,
                            )
                        }
                    }

                    is ApiResponse.Error -> {
                        _event.emit(
                            UploadVideoEvent.Error(
                                errorCode = response.errorCode,
                                errorMessage = response.errorMessage,
                            ),
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    fun insertPost() {
        viewModelScope.launch {
            postRepository.insertPost(
                postRequest = InsertPostRequest(
                    title = uiState.value.topic,
                    videoUrl = uiState.value.videoUri,
                    tagList = uiState.value.selectedTags,
                ),
            ).collectLatest { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                videoUri = response.data,
                            )
                        }
                        _event.emit(UploadVideoEvent.Success)
                    }

                    is ApiResponse.Error -> {
                        _event.emit(
                            UploadVideoEvent.Error(
                                errorCode = response.errorCode,
                                errorMessage = response.errorMessage,
                            ),
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _uiState.update { uiState ->
            uiState.copy(
                topic = s.toString(),
            )
        }
    }
}
