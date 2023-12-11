package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.BuildConfig
import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.constants.Constants
import com.tourify.tourifyapp.model.RegistrationResponse

class CheckEmailRepository(private val apiService: ApiService) {
    suspend fun login(email: String): RegistrationResponse {
        return apiService.registration(
            email = email
        )
    }

    companion object {
        @Volatile
        private var instance: CheckEmailRepository? = null
        fun getInstance(
            apiService: ApiService
        ): CheckEmailRepository =
            instance ?: synchronized(this) {
                instance ?: CheckEmailRepository(apiService)
            }.also { instance = it }
    }
}
