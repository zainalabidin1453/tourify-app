package com.tourify.tourifyapp.model

data class LogoutResponse(
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

