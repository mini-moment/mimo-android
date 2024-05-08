package com.mimo.android.data.repositoryimpl

import com.google.gson.Gson
import com.mimo.android.data.datasource.remote.MapRemoteDataSource
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.model.response.ErrorResponse
import com.mimo.android.data.model.response.apiHandler
import com.mimo.android.data.repository.MapRepository
import com.mimo.android.domain.model.MarkerData
import kotlinx.coroutines.flow.internal.NopCollector.emit
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
  private val mapRemoteDataSource: MapRemoteDataSource
) : MapRepository {
  override suspend fun getMarkers(latitude: Long, longitude: Long, radius: Long): ApiResponse<MarkerData> {
    val response = apiHandler {
      val result = mapRemoteDataSource.getMarkers(latitude, longitude, radius)
      val errorData = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
      Pair(result, errorData)
    }
    return when (response) {
      is ApiResponse.Success -> {
        ApiResponse.Success(data = response.data)
      }

      is ApiResponse.Error -> {
        emit(
          ApiResponse.Error(
            errorCode = response.errorCode,
            errorMessage = response.errorMessage,
          ),
        )
      }

      else -> {}
    }
  }
}