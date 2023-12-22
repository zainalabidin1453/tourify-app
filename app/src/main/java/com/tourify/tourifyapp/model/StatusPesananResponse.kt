package com.tourify.tourifyapp.model

data class StatusPesananResponse(
    val error: String? = null,
    val message: String,
    val statusCode: Int,
    val statusPayment: Int
)
