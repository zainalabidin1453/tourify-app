package com.tourify.tourifyapp.model

data class DestinationsResponse(
    val statusCode: Int,
    val message: String,
    val error: String? = null,
    val data: List<DataDestinationsResponse>? = null
)

data class DataDestinationsResponse(
    val id: Int,
    val name: String,
    val type: String,
    val subdistrict: String,
    val regency: String,
    val province: String,
    val rating: Int,
    val openOn: String,
    val closedOn: String,
    val ticketPrice: Int,
    val caption: String,
    val photo: String,
    val lat: Double,
    val lon: Double,
    val lastUpdate: String,
)

