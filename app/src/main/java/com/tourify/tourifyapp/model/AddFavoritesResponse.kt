package com.tourify.tourifyapp.model

data class AddFavoritesResponse(
    val statusCode: Int,
    val message: String,
    val error: String? = null
)
