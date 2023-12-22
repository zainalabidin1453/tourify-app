package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.DestinationsResponse

class DestinationsRepository(private val apiService: ApiService) {
    suspend fun getDestinations(): DestinationsResponse {
        return apiService.getDestinations()
    }

    companion object {
        @Volatile
        private var instance: DestinationsRepository? = null
        fun getInstance(
            apiService: ApiService
        ): DestinationsRepository =
            instance ?: synchronized(this) {
                instance ?: DestinationsRepository(apiService)
            }.also { instance = it }
    }
}
