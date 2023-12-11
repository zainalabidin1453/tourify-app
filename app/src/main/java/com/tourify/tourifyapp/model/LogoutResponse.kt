package com.tourify.tourifyapp.model

data class LogoutResponse(
    val statusCode: Int,
    val message: String,
    val error: String? = null
)

