package com.tourify.tourifyapp.model

data class CreatePasswordResponse(
    val statusCode: Int,
    val message: String,
    val data: DataCreatePasswordResponse? = null
)

data class DataCreatePasswordResponse(
    val userId: Int,
    val email: String
)

