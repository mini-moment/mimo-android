package com.mimo.domain.repository

import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.MarkerData

interface MapRepository {

    suspend fun getMarkers(
        latitude: Double,
        longitude: Double,
        radius: Double,
    ): ApiResponse<List<MarkerData>>
}
