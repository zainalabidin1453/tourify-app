package com.tourify.tourifyapp.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val data: DataLoginResponse? = null,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

@Serializable
data class DataLoginResponse(
    val email: String,
    val userId: String
)
