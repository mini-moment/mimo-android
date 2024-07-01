package com.mimo.android.presentation.map

interface MarkerEvent {

    data class LeafMarker(val idx: Int) : MarkerEvent

    data class ClusterMarker(
        val idxList: List<Int>,
        val latitude: Double,
        val longitude: Double,
    ) : MarkerEvent

    data class LongClickMarker(
        val latitude: Double,
        val longitude: Double,
        val address: String,
    ) : MarkerEvent
}
