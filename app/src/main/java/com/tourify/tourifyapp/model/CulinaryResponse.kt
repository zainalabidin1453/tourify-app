package com.tourify.tourifyapp.model

data class CulinaryResponse(
    val data: List<DataCulinaryResponse>? = null,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

data class DataCulinaryResponse(
    val caption: String,
    val country: String,
    val id: Int,
    val name: String,
    val photo: String,
    val province: String,
    val regency: String
)

