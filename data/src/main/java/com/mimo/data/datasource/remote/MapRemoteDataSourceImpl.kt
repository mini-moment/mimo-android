package com.mimo.data.datasource.remote

import com.mimo.data.api.MapApi
import com.mimo.data.model.MarkerResponse
import retrofit2.Response
import javax.inject.Inject

class MapRemoteDataSourceImpl @Inject constructor(
    private val mapApi: MapApi,
) : MapRemoteDataSource {

    override suspend fun getMarkers(
        latitude: Double,
        longitude: Double,
        radius: Double,
    ): Response<MarkerResponse> {
        return mapApi.getMarkers(latitude, longitude, radius)
    }
}
