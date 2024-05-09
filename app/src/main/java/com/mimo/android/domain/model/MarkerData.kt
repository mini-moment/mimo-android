package com.mimo.android.domain.model

import com.mimo.android.data.model.response.MarkerResponse
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.clustering.ClusteringKey

data class MarkerData(
    val latitude: Double,
    val longitude: Double
) : ClusteringKey {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)

    companion object {
        val DEFAULT = mutableListOf<MarkerData>(
            MarkerData(36.107003, 128.418207),
            MarkerData(
                36.106991, 128.419542
            ),
            MarkerData(
                36.108106, 128.418465
            ),
            MarkerData(36.107702, 128.420665),
            MarkerData(36.106047, 128.420559)
        )
    }
}


fun MarkerResponse.toMarkerData(): List<MarkerData> {
    return this.data.map {
        MarkerData(
            latitude = it.latitude.toDouble(),
            longitude = it.longitude.toDouble()
        )
    }
}
