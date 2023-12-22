package com.tourify.tourifyapp.model

data class FavoritesResponse(
    val data: List<Int>,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

