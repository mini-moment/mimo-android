package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.MarkerResponse
import retrofit2.Response

interface MapRemoteDataSource {

  suspend fun getMarkers(latitude : Double, longitude : Double, radius : Double) : Response<MarkerResponse>
}