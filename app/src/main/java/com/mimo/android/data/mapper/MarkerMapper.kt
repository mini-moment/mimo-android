package com.mimo.android.data.mapper

import com.mimo.android.data.model.MarkerResponse
import com.mimo.android.domain.model.MarkerData

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
