package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.CulinaryResponse

class CulinaryRepository(private val apiService: ApiService) {
    suspend fun getCulinary(): CulinaryResponse {
        return apiService.getCulinary()
    }

    companion object {
        @Volatile
        private var instance: CulinaryRepository? = null
        fun getInstance(
            apiService: ApiService
        ): CulinaryRepository =
            instance ?: synchronized(this) {
                instance ?: CulinaryRepository(apiService)
            }.also { instance = it }
    }
}
