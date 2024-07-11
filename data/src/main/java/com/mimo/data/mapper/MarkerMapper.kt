package com.mimo.data.mapper

import com.mimo.data.model.MarkerResponse
import com.mimo.domain.model.MarkerData

fun MarkerResponse.toMarkerData(): List<MarkerData> {
    return this.data.map {
        MarkerData(
            id = it.id,
            latitude = it.latitude,
            longitude = it.longitude,
            postId = it.postId,
        )
    }
}
