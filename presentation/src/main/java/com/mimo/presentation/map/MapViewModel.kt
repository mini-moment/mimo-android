package com.mimo.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.Post
import com.mimo.domain.repository.MapRepository
import com.mimo.domain.repository.PostRepository
import com.mimo.presentation.util.UiState
import com.naver.maps.map.clustering.Clusterer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel
    @Inject
    constructor(
        private val mapRepository: MapRepository,
        private val postRepository: PostRepository,
    ) : ViewModel() {
        private val _markerList = MutableLiveData<List<MapMarkerData>>()
        val markerList: LiveData<List<MapMarkerData>> get() = _markerList

        fun setMarkerList(markers: List<MapMarkerData>) {
            _markerList.value = markers
        }

        private val _currentMarkerList = MutableLiveData<Clusterer<MapMarkerData>>()
        val currentMarkerList: LiveData<Clusterer<MapMarkerData>> get() = _currentMarkerList

        fun setCurrentMarkerList(value: Clusterer<MapMarkerData>) {
            _currentMarkerList.value = value
        }

        private val _event = MutableSharedFlow<MarkerEvent>()
        val event: SharedFlow<MarkerEvent> = _event

        private val _postState: MutableStateFlow<UiState<List<Post>>> =
            MutableStateFlow(UiState.Loading)
        val postState: StateFlow<UiState<List<Post>>> = _postState

        fun setPostState(type: UiState<List<Post>>) {
            _postState.value = type
        }

        fun setMarkerEvent(type: MarkerEvent) {
            viewModelScope.launch {
                if (type is MarkerEvent.LongClickMarker)
                    {
                        _event.emit(type)
                        return@launch
                    }

                if (postState.value is UiState.Success) {
                    _event.emit(type)
                }
            }
        }

        fun getMarkerList(
            latitude: Double,
            longitude: Double,
            radius: Double,
        ) {
            viewModelScope.launch {
                when (val response = mapRepository.getMarkers(latitude, longitude, radius)) {
                    is ApiResponse.Success -> {
                        val mapMarkers = response.data.map { it.toMapMarkerData() }
                        setMarkerList(mapMarkers)
                        getPostList(response.data.map { it.postId })
                        Timber.d("마커 불러오기 성공! ${response.data}")
                    }

                    is ApiResponse.Error -> {
                        setPostState(UiState.Error(response.errorMessage))
                        Timber.d("마커 불러오기 에러 발생! ${response.errorMessage}")
                    }

                    is ApiResponse.Failure -> {
                        setPostState(UiState.Error(""))
                        Timber.d("마커 불러오기 알 수 없는 에러 발생!")
                    }
                }
            }
        }

        private fun getPostList(ids: List<Int>) {
            viewModelScope.launch {
                postRepository.getPostLists(ids).collectLatest {
                    when (val response = it) {
                        is ApiResponse.Success -> {
                            _postState.emit(UiState.Success(response.data))
                            Timber.d("게시글 불러오기 성공! ${response.data}!")
                        }

                        is ApiResponse.Error -> {
                            _postState.emit(UiState.Error(response.errorMessage))
                            Timber.d("게시글 불러오기 에러 발생! ${response.errorMessage}!")
                        }

                        is ApiResponse.Failure -> {
                            Timber.d("게시글 불러오기 알 수 없는 에러 발생!")
                        }
                    }
                }
            }
        }
    }
