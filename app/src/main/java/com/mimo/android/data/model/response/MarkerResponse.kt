package com.mimo.android.data.model.response

data class MarkerResponse(
    val data: List<MarkerItemResponse>,
    val statusCode: String? = null,
    val message: String? = null,
) {
    data class MarkerItemResponse(
        val id: Int,
        val postId : Int,
        val latitude: Double,
        val longitude: Double
    )

}


