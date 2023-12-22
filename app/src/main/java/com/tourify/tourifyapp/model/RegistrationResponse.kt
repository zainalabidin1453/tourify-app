package com.tourify.tourifyapp.model

data class RegistrationResponse(
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

