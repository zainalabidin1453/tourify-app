package com.tourify.tourifyapp.model

data class RegistrationResponse(
    val statusCode: Int,
    val message: String,
    val error: String? = null
)

