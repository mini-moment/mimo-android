package com.mimo.android.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.repository.MapRepository
import com.mimo.android.domain.model.MarkerData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository
) : ViewModel() {

    private val _markerList = MutableLiveData<List<MarkerData>>()
    val markerList : LiveData<List<MarkerData>> get() = _markerList

    fun setMarkerList(markers : List<MarkerData>){
        _markerList.value = markers
    }


    fun getMarkerList(latitude : Long, longitude : Long, radius : Long ){
        viewModelScope.launch {
            when(val response = mapRepository.getMarkers(latitude, longitude, radius)){
                is ApiResponse.Success -> {
                    setMarkerList(response.data)
                    Timber.d("마커 불러오기 성공! ${response.data}")
                }
                is ApiResponse.Error -> {
                    Timber.d("마커 불러오기 에러 발생! ${response.errorMessage}")
                }
                is ApiResponse.Failure -> {
                    Timber.d("마커 불러오기 알 수 없는 에러 발생!")
                }
            }
        }
    }


}