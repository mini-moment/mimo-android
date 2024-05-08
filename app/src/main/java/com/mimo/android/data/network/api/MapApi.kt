package com.mimo.android.data.network.api

import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.MarkerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApi {

  @GET("markers")
  suspend fun getMarkers(
    @Query("latitude") latitude: Long,
    @Query("longitude") longitude: Long,
    @Query("radius") radius: Long
  ): Response<List<MarkerResponse>>

}