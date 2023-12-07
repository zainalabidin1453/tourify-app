package com.tourify.tourifyapp.model

data class LoginResponse(
    val statusCode: Int,
    val message: String,
    val data: DataLoginResponse? = null
)

data class DataLoginResponse(
    val userId: Int,
    val email: String
)
