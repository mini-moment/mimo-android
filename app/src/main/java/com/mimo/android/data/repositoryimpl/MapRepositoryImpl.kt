package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.remote.MapRemoteDataSource
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.ErrorResponse
import com.mimo.android.data.model.response.apiHandler
import com.mimo.android.data.repository.MapRepository
import com.mimo.android.domain.model.MarkerData
import com.mimo.android.domain.model.toMarkerData
import timber.log.Timber
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapRemoteDataSource: MapRemoteDataSource
) : MapRepository {
    override suspend fun getMarkers(
        latitude: Long,
        longitude: Long,
        radius: Long
    ): ApiResponse<List<MarkerData>> {
        val response = apiHandler {
            val result = mapRemoteDataSource.getMarkers(latitude, longitude, radius)
            val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
            Pair(result, errorData)
        }
        return when (response) {
            is ApiResponse.Success -> {
                Timber.d("마커 확인 ${response.data}")
                ApiResponse.Success(data = response.data.toMarkerData())
            }

            is ApiResponse.Error -> {
                Timber.d("마커 확인 ${response}")
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