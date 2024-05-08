package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.MarkerResponse
import com.mimo.android.data.network.api.MapApi
import retrofit2.Response
import javax.inject.Inject

class MapRemoteDataSourceImpl @Inject constructor(
  private val mapApi: MapApi
) : MapRemoteDataSource {

  override suspend fun getMarkers(
    latitude: Long,
    longitude: Long,
    radius: Long
  ): Response<List<MarkerResponse>> {
    return mapApi.getMarkers(latitude, longitude, radius)
  }
}