package com.mimo.data.api

import com.mimo.data.model.MarkerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApi {
    @GET("markers")
    suspend fun getMarkers(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Double,
    ): Response<MarkerResponse>
}
