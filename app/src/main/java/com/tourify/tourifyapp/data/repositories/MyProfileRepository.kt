package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.MyProfileResponse

class MyProfileRepository(private val apiService: ApiService) {
    suspend fun getMyProfile(userId: Int): MyProfileResponse {
        return apiService.getMyProfile(userId)
    }

    companion object {
        @Volatile
        private var instance: MyProfileRepository? = null
        fun getInstance(
            apiService: ApiService
        ): MyProfileRepository =
            instance ?: synchronized(this) {
                instance ?: MyProfileRepository(apiService)
            }.also { instance = it }
    }
}
