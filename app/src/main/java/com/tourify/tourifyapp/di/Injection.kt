package com.tourify.tourifyapp.di

import com.tourify.tourifyapp.api.ApiConfig
import com.tourify.tourifyapp.data.repositories.CheckEmailRepository

object Injection {
    fun provideCheckEmailRepository(): CheckEmailRepository {
        val apiService = ApiConfig.getApiService()
        return CheckEmailRepository.getInstance(apiService)
    }
}