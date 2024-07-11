package com.mimo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MarkerData(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val postId: Int,
)
