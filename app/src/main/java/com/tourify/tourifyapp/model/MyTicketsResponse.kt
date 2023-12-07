package com.tourify.tourifyapp.model

data class MyTicketsResponse(
    val statusCode: Int,
    val message: String,
    val data: List<DataMyTicketsResponseResponse>? = null
)

data class DataMyTicketsResponseResponse(
    val id: Int,
    val destinationsId: Int,
    val codeBooking: String,
    val statusCheckIn: Int,
    val dateBooking: String,
    val dateTrip: String,
    val withTourGuide: Boolean,
    val tourGuideId: Int,
    val statusPayment: Int,
    val totalTicket: String,
    val ticketPrice: Int,
    val totalServicesTourGuide: Int,
    val totalDiscount: Int,
    val applicationFee: Int,
    val totalPrice: Int,
    val notes: String,
    val dataTourGuide: DataTourGuideMyTicketsResponse? = null
)

data class DataTourGuideMyTicketsResponse(
    val id: Int,
    val name: String,
    val email: String,
    val noWhatsapp: String,
    val servicePrice: Int,
    val photoProfile: String,
    val totalReview: String,
    val rating: Double,
    val totalTrip: Int
)

