package com.mimo.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.data.datasource.remote.MapRemoteDataSource
import com.mimo.data.mapper.toMarkerData
import com.mimo.data.model.apiHandler
import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.ErrorResponse
import com.mimo.domain.model.MarkerData
import com.mimo.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapRemoteDataSource: MapRemoteDataSource,
) : MapRepository {
    override suspend fun getMarkers(
        latitude: Double,
        longitude: Double,
        radius: Double,
    ): ApiResponse<List<MarkerData>> {
        val response = apiHandler {
            val result = mapRemoteDataSource.getMarkers(latitude, longitude, radius)
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        return when (response) {
            is ApiResponse.Success -> {
                ApiResponse.Success(data = response.data.toMarkerData())
            }

            is ApiResponse.Error -> {
                ApiResponse.Error(
                    errorCode = response.errorCode,
                    errorMessage = response.errorMessage,
                )
            }

            else -> {
                ApiResponse.Failure
            }
        }
    }
}
