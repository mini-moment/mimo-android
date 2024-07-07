package com.mimo.android.domain.repository

import com.mimo.android.domain.model.ApiResponse
import com.mimo.android.domain.model.MarkerData

interface MapRepository {

    suspend fun getMarkers(
        latitude: Double,
        longitude: Double,
        radius: Double,
    ): ApiResponse<List<MarkerData>>
}
