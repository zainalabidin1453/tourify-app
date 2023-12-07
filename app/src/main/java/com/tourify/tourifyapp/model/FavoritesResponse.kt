package com.tourify.tourifyapp.model

data class FavoritesResponse(
    val statusCode: Int,
    val message: String,
    val data: List<DataFavoritesResponse>? = null
)

data class DataFavoritesResponse(
    val destinationsId: Int
)

