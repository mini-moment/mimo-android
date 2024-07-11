package com.mimo.presentation.map

import com.mimo.domain.model.MarkerData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.clustering.ClusteringKey

data class MapMarkerData(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val postId: Int,
) : ClusteringKey {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)
}

fun MarkerData.toMapMarkerData(): MapMarkerData {
    return MapMarkerData(
        id = id,
        latitude = latitude,
        longitude = longitude,
        postId = postId,
    )
}
