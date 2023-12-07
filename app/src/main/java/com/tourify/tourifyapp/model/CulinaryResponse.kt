package com.tourify.tourifyapp.model

data class CulinaryResponse(
    val statusCode: Int,
    val message: String,
    val data: List<DataCulinaryResponse>? = null
)

data class DataCulinaryResponse(
    val id: Int,
    val name: String,
    val regency: String,
    val province: String,
    val country: String,
    val caption: String,
    val photo: String
)

