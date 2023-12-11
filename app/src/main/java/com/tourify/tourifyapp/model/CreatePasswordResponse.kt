package com.tourify.tourifyapp.model

data class CreatePasswordResponse(
    val statusCode: Int,
    val message: String,
    val error: String? = null,
    val data: DataCreatePasswordResponse
)

data class DataCreatePasswordResponse(
    val userId: Int,
    val email: String
)

