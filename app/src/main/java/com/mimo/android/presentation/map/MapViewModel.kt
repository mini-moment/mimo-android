package com.mimo.android.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.repository.MapRepository
import com.mimo.android.data.repository.PostRepository
import com.mimo.android.domain.model.MarkerData
import com.mimo.android.domain.model.PostData
import com.mimo.android.presentation.util.UiState
import com.naver.maps.map.clustering.Clusterer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    private val _markerList = MutableLiveData<List<MarkerData>>()
    val markerList: LiveData<List<MarkerData>> get() = _markerList

    fun setMarkerList(markers: List<MarkerData>) {
        _markerList.value = markers
    }

    private val _currentMarkerList = MutableLiveData<Clusterer<MarkerData>>()
    val currentMarkerList: LiveData<Clusterer<MarkerData>> get() = _currentMarkerList

    fun setCurrentMarkerList(value: Clusterer<MarkerData>) {
        _currentMarkerList.value = value
    }

    private val _event = MutableSharedFlow<MarkerEvent>()
    val event: SharedFlow<MarkerEvent> = _event

    private val _postState: MutableStateFlow<UiState<List<PostData>>> =
        MutableStateFlow(UiState.Loading)
    val postState: StateFlow<UiState<List<PostData>>> = _postState

    fun setPostState(type : UiState<List<PostData>>){
        _postState.value = type
    }

    fun setMarkerEvent(type: MarkerEvent) {
        viewModelScope.launch {
            if(postState.value is UiState.Success){
                _event.emit(type)
            }
        }
    }

    fun getMarkerList(latitude: Double, longitude: Double, radius: Double) {
        viewModelScope.launch {
            when (val response = mapRepository.getMarkers(latitude, longitude, radius)) {
                is ApiResponse.Success -> {
                    setMarkerList(response.data)
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
