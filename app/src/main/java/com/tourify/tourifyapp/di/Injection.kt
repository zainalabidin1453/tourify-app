package com.tourify.tourifyapp.di

import com.tourify.tourifyapp.api.ApiConfig
import com.tourify.tourifyapp.data.repositories.BookingRepository
import com.tourify.tourifyapp.data.repositories.CheckEmailRepository
import com.tourify.tourifyapp.data.repositories.CreatePasswordRepository
import com.tourify.tourifyapp.data.repositories.CulinaryRepository
import com.tourify.tourifyapp.data.repositories.DestinationsRepository
import com.tourify.tourifyapp.data.repositories.FavoriteRepository
import com.tourify.tourifyapp.data.repositories.LoginRepository
import com.tourify.tourifyapp.data.repositories.MyProfileRepository
import com.tourify.tourifyapp.data.repositories.MyTicketsRepository
import com.tourify.tourifyapp.data.repositories.RecommendationRepository
import com.tourify.tourifyapp.data.repositories.ScanningObjectRepository
import com.tourify.tourifyapp.data.repositories.StatusPesananRepository
import com.tourify.tourifyapp.data.repositories.VerificationCodeRepository

object Injection {
    fun provideBookingRepository(): BookingRepository {
        val apiService = ApiConfig.getApiService()
        return BookingRepository.getInstance(apiService)
    }

    fun provideCheckEmailRepository(): CheckEmailRepository {
        val apiService = ApiConfig.getApiService()
        return CheckEmailRepository.getInstance(apiService)
    }

    fun provideCreatePasswordRepository(): CreatePasswordRepository {
        val apiService = ApiConfig.getApiService()
        return CreatePasswordRepository.getInstance(apiService)
    }

    fun provideCulinaryRepository(): CulinaryRepository {
        val apiService = ApiConfig.getApiService()
        return CulinaryRepository.getInstance(apiService)
    }

    fun provideDestinationsRepository(): DestinationsRepository {
        val apiService = ApiConfig.getApiService()
        return DestinationsRepository.getInstance(apiService)
    }

    fun provideFavoriteRepository(): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        return FavoriteRepository.getInstance(apiService)
    }

    fun provideLoginRepository(): LoginRepository {
        val apiService = ApiConfig.getApiService()
        return LoginRepository.getInstance(apiService)
    }

    fun provideMyProfileRepository(): MyProfileRepository {
        val apiService = ApiConfig.getApiService()
        return MyProfileRepository.getInstance(apiService)
    }

    fun provideMyTicketsRepository(): MyTicketsRepository {
        val apiService = ApiConfig.getApiService()
        return MyTicketsRepository.getInstance(apiService)
    }

    fun provideRecommendationRepository(): RecommendationRepository {
        val apiService = ApiConfig.getApiService()
        return RecommendationRepository.getInstance(apiService)
    }

    fun provideScanningObjectRepository(): ScanningObjectRepository {
        val apiService = ApiConfig.getApiService()
        return ScanningObjectRepository.getInstance(apiService)
    }

    fun provideStatusPesananRepository(): StatusPesananRepository {
        val apiService = ApiConfig.getApiService()
        return StatusPesananRepository.getInstance(apiService)
    }

    fun provideVerificationCodeRepository(): VerificationCodeRepository {
        val apiService = ApiConfig.getApiService()
        return VerificationCodeRepository.getInstance(apiService)
    }
}