package com.mimo.android.presentation.util


import com.mimo.android.domain.model.MarkerData
import com.naver.maps.map.clustering.Clusterer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun makeMarker(marker: List<MarkerData>): Clusterer<MarkerData> { // cluster 연결
  val cluster: Clusterer<MarkerData> = Clusterer.Builder<MarkerData>().build()
  withContext(Dispatchers.Default) {
    marker.forEachIndexed { index, item ->
      cluster.add(item, null)
    }
  }
  return cluster
}

suspend fun deleteMarker(marker : Clusterer<MarkerData>){
  withContext(Dispatchers.Default){
    marker.map = null
  }
}
