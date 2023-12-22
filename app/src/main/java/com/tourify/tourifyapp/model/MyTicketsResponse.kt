package com.tourify.tourifyapp.model

data class MyTicketsResponse(
    val data: List<DataMyTicketsResponse>? = null,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

data class DataMyTicketsResponse(
    val bookingCode: String,
    val bookingDate: String,
    val checkInDate: String? = null,
    val destinationId: Int,
    val id: Int,
    val dateTrip: String,
    val ordererNote: String,
    val statusPayment: Int,
    val totalPayment: Int,
    val tourGuideData: DataTourGuideMyTicketsResponse? = null,
    val tourGuideId: Int,
    val tripDate: String,
    val userId: String
)

data class DataTourGuideMyTicketsResponse(
    val email: String,
    val id: Int,
    val name: String,
    val photoProfile: String,
    val servicePrice: Int,
    val totalTrip: Int
)

