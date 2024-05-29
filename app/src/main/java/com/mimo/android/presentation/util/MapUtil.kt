package com.mimo.android.presentation.util


import com.mimo.android.domain.model.MarkerData
import com.naver.maps.map.clustering.ClusterMarkerInfo
import com.naver.maps.map.clustering.Clusterer
import com.naver.maps.map.clustering.DefaultClusterMarkerUpdater
import com.naver.maps.map.clustering.DefaultLeafMarkerUpdater
import com.naver.maps.map.clustering.LeafMarkerInfo
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend fun makeMarker(
    marker: List<MarkerData>,
    builder: Clusterer.ComplexBuilder<MarkerData>
): Clusterer<MarkerData> { // cluster 연결
    val cluster: Clusterer<MarkerData> =
        builder.positioningStrategy { clusterer ->
            clusterer.children.first().coord
        }.build()

    withContext(Dispatchers.Default) {
        marker.forEachIndexed { index, item ->
            cluster.add(item, item)
        }
    }
    return cluster
}

suspend fun deleteMarker(marker: Clusterer<MarkerData>) {
    withContext(Dispatchers.Default) {
        marker.map = null
    }
}


fun clickMarker(
    builder: Clusterer.ComplexBuilder<MarkerData>,
    markerInfo: (MarkerData) -> Unit
) {
    builder.clusterMarkerUpdater(object : DefaultClusterMarkerUpdater() {
        override fun updateClusterMarker(info: ClusterMarkerInfo, marker: Marker) {
            super.updateClusterMarker(info, marker)

            marker.onClickListener = Overlay.OnClickListener {

                true
            }
        }
    }).leafMarkerUpdater(object : DefaultLeafMarkerUpdater() {
        override fun updateLeafMarker(info: LeafMarkerInfo, marker: Marker) {
            super.updateLeafMarker(info, marker)
            marker.onClickListener = Overlay.OnClickListener {
                Timber.d("마커 클릭 정보 ${info.key}, $marker")
                val markerData = info.key as MarkerData
                markerInfo(markerData)
                true
            }
        }
    })
}





