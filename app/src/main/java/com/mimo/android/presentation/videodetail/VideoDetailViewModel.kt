package com.mimo.android.presentation.videodetail

import androidx.lifecycle.ViewModel
import com.mimo.android.domain.model.PostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
) : ViewModel() {
    private val _event = MutableSharedFlow<VideoDetailEvent>()
    val event: SharedFlow<VideoDetailEvent> = _event
    private val _uiState = MutableStateFlow(VideoDetailUiState())
    val uiState: StateFlow<VideoDetailUiState> = _uiState

    fun setPostList(postList: List<PostData>?) {
        if (postList != null) {
            _uiState.update { uiState ->
                uiState.copy(
                    posts = postList
                )
            }
        }
    }
}
