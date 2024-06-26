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

suspend fun makeMarker(
    marker: List<MarkerData>,
    builder: Clusterer.ComplexBuilder<MarkerData>,

    ): Clusterer<MarkerData> { // cluster 연결
    val cluster: Clusterer<MarkerData> =
        builder.tagMergeStrategy { cluster ->
            cluster.children.map { it.tag }.joinToString(",")
        }.build()

    withContext(Dispatchers.Default) {
        marker.forEach { item ->
            cluster.add(item, "${item.postId}")
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
    markerInfo: (MarkerData) -> Unit?,
    clusterTag: (List<Int>, Double, Double) -> Unit?
) {
    builder.clusterMarkerUpdater(object : DefaultClusterMarkerUpdater() {

        override fun updateClusterMarker(info: ClusterMarkerInfo, marker: Marker) {
            super.updateClusterMarker(info, marker)
            marker.onClickListener = Overlay.OnClickListener {
                val idList = (info.tag as String).split(",").map { it.toInt() }
                clusterTag(idList, marker.position.latitude, marker.position.longitude)
                false
            }
        }
    }).leafMarkerUpdater(object : DefaultLeafMarkerUpdater() {
        override fun updateLeafMarker(info: LeafMarkerInfo, marker: Marker) {
            super.updateLeafMarker(info, marker)
            marker.onClickListener = Overlay.OnClickListener {
                val markerData = info.key as MarkerData
                markerInfo(markerData)
                true
            }
        }
    })
}
