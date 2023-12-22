package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.AddFavoritesResponse
import com.tourify.tourifyapp.model.FavoritesResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class FavoriteRepository(private val apiService: ApiService) {
    suspend fun getFavorites(userId: Int): FavoritesResponse {
        return apiService.getFavorites(userId)
    }

    suspend fun addFavorites(userId: Int, destinationId: Int): AddFavoritesResponse {
        val jsonObject = JSONObject().apply {
            put("userId", userId)
            put("destinationId", destinationId)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.addFavorites(requestBody)
    }

    suspend fun deleteFavorites(userId: Int, destinationId: Int): AddFavoritesResponse {
        val jsonObject = JSONObject().apply {
            put("userId", userId)
            put("destinationId", destinationId)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.addFavorites(requestBody)
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService)
            }.also { instance = it }
    }
}
