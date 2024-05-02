package com.mimo.android.presentation.util


import com.mimo.android.domain.model.MarkerData
import com.naver.maps.map.clustering.Clusterer

suspend fun makeMarker(marker: List<MarkerData>): Clusterer<MarkerData> {
  val cluster: Clusterer<MarkerData> = Clusterer.Builder<MarkerData>().build()

  marker.forEachIndexed { index, item ->
    cluster.add(item, null)
  }

  return cluster
}