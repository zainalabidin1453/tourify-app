package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.ScanningObjectResponse
import okhttp3.MultipartBody

class ScanningObjectRepository(private val apiService: ApiService) {
    suspend fun scanningObject(imageFile: MultipartBody.Part): ScanningObjectResponse {
        return apiService.scanningObject(imageFile)
    }

    companion object {
        @Volatile
        private var instance: ScanningObjectRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ScanningObjectRepository =
            instance ?: synchronized(this) {
                instance ?: ScanningObjectRepository(apiService)
            }.also { instance = it }
    }
}
