package com.tourify.tourifyapp.model

data class MyProfileResponse(
    val statusCode: Int,
    val message: String,
    val data: DataMyProfileResponse? = null
)

data class DataMyProfileResponse(
    val userId: Int,
    val name: String,
    val email: String,
    val username: String,
    val dateBirth: String,
    val gender: Int,
    val address: String,
    val village: String,
    val subdistrict: String,
    val regency: String,
    val province: String,
    val country: String,
    val zipCode: String,
    val citizenship: String,
    val noTelephone: String,
    val statusAccount: Int,
    val isVerification: Boolean
)



