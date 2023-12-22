package com.tourify.tourifyapp.model

data class MyProfileResponse(
    val data: List<DataMyProfileResponse>? = null,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

data class DataMyProfileResponse(
    val id: String,
    val name: String,
    val email: String,
    val username: String
)



