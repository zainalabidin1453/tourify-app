package com.tourify.tourifyapp.model

data class FavoritesResponse(
    val statusCode: Int,
    val message: String,
    val error: String? = null,
    val data: List<Int>
)

