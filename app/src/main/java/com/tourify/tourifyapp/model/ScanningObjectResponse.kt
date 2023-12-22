package com.tourify.tourifyapp.model

data class ScanningObjectResponse(
    val data: DataScanningObjectResponse? = null,
    val error: String? = null,
    val message: String,
    val statusCode: Int
)

data class DataScanningObjectResponse(
    val name: String,
    val caption: String,
    val photo: String
)

