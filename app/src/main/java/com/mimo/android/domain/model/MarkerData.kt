package com.mimo.android.domain.model

import com.mimo.android.data.model.response.MarkerResponse
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.clustering.ClusteringKey


data class MarkerData(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val postId: Int
) : ClusteringKey {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)

}

fun MarkerResponse.toMarkerData(): List<MarkerData> {
    return this.data.map {
        MarkerData(
            id = it.id,
            latitude = it.latitude,
            longitude = it.longitude,
            postId = it.postId
        )
    }
}
