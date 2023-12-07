package com.tourify.tourifyapp.model

data class DestinationsResponse(
    val statusCode: Int,
    val message: String,
    val data: List<DataDestinationsResponse>? = null
)

data class DataDestinationsResponse(
    val id: Int,
    val name: String,
    val type: String,
    val regency: String,
    val province: String,
    val country: String,
    val rating: Double,
    val totalReview: Int,
    val totalCulinary: Int,
    val openOn: String,
    val ticketPrice: Int,
    val caption: String,
    val photo: String,
    val lat: Double,
    val lon: Double,
    val tourGuide: List<DataTourGuideResponse>
)

data class DataTourGuideResponse(
    val id: Int,
    val name: String,
    val email: String,
    val servicePrice: Int,
    val photoProfile: String,
    val totalReview: String,
    val rating: Double,
    val totalTrip: Int
)

