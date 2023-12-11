package com.tourify.tourifyapp.model

data class VerificationResponse(
    val statusCode: Int,
    val message: String,
    val error: String? = null
)

