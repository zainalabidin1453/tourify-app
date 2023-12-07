package com.tourify.tourifyapp.model

data class ScanningObjectResponse(
    val statusCode: Int,
    val message: String,
    val data: DataScanningObjectResponse? = null
)

data class DataScanningObjectResponse(
    val name: String,
    val type: String,
    val description: String,
    val photo: String
)

