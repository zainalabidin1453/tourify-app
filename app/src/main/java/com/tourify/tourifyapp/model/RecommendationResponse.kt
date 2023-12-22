package com.tourify.tourifyapp.model

data class RecommendationResponse(
    val data: DataRecommendation,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

data class DataRecommendation(
    val recommendations: List<DataRecommendationsResponse>? = null,
    val userId: Int
)

data class DataRecommendationsResponse(
    val userId: Int,
    val country: String,
    val id: Int,
    val name: String,
    val newRating: Int,
    val regency: String,
    val subdistrict: String,
    val type: String,
    val typeLabel: Int,
    val village: String
)

