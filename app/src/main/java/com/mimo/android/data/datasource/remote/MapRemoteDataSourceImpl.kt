package com.mimo.android.data.datasource.remote

import com.mimo.android.data.model.response.MarkerResponse
import com.mimo.android.data.network.api.MapApi
import retrofit2.Response
import javax.inject.Inject

class MapRemoteDataSourceImpl @Inject constructor(
    private val mapApi: MapApi
) : MapRemoteDataSource {

    override suspend fun getMarkers(
        latitude: Double,
        longitude: Double,
        radius: Double
    ): Response<MarkerResponse> {
        
        return mapApi.getMarkers(latitude, longitude, radius)
    }
}