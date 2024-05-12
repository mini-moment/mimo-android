package com.mimo.android.data.repository

import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.domain.model.MarkerData

interface MapRepository {

   suspend fun getMarkers(latitude : Double, longitude : Double, radius : Double) : ApiResponse<List<MarkerData>>
}