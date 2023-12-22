package com.tourify.tourifyapp.model

data class DestinationsResponse(
    val data: List<DataDestinationsResponse>? = null,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

data class DataDestinationsResponse(
    val caption: String,
    val closedOn: String,
    val id: Int,
    val lastUpdate: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val openOn: String,
    val photo: String,
    val province: String,
    val rating: Double,
    val regency: String,
    val subdistrict: String,
    val ticketPrice: Int,
    val tourGuide: List<DataTourGuideResponse>? = null,
    val type: String
)

data class DataTourGuideResponse(
    val email: String,
    val id: String,
    val name: String,
    val photoProfile: String,
    val rating: Double,
    val servicePrice: Int,
    val totalTrip: Int
)

