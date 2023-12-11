package com.tourify.tourifyapp.model

data class BookingTripResponse(
    val statusCode: Int,
    val message: String,
    val error: String? = null,
    val data: DataBookingTripResponse? = null
)

data class DataBookingTripResponse(
    val id: Int,
    val code: String,
    val total: String,
    val withTourGuide: Boolean,
    val statusPayment: Int
)

