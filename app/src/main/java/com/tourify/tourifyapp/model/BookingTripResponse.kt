package com.tourify.tourifyapp.model

data class BookingTripResponse(
    val data: DataBookingTripResponse? = null,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

data class DataBookingTripResponse(
    val code: String,
    val id: Int,
    val statusPayment: Int,
    val total: Int,
    val withTourGuide: Boolean
)

