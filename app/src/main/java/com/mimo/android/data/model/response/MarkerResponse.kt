package com.mimo.android.data.model.response

import com.mimo.android.domain.model.MarkerData

data class MarkerResponse(
  val id: Int,
  val name: String,
  val latitude: String,
  val longitude: String
)


fun MarkerResponse.toMarkerData(): MarkerData {
  return MarkerData(
    latitude = this.latitude.toDouble(),
    longitude = this.longitude.toDouble()
  )
}