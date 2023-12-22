package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.MyTicketsResponse

class MyTicketsRepository(private val apiService: ApiService) {
    suspend fun getMyTickets(userId: Int): MyTicketsResponse {
        return apiService.getMyTickets(userId)
    }

    companion object {
        @Volatile
        private var instance: MyTicketsRepository? = null
        fun getInstance(
            apiService: ApiService
        ): MyTicketsRepository =
            instance ?: synchronized(this) {
                instance ?: MyTicketsRepository(apiService)
            }.also { instance = it }
    }
}
