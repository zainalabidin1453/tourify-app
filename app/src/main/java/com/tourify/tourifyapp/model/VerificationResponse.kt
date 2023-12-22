package com.tourify.tourifyapp.model

data class VerificationResponse(
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

