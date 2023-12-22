package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.RecommendationResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RecommendationRepository(private val apiService: ApiService) {
    suspend fun getRecommendation(userId: Int): RecommendationResponse {
        val jsonObject = JSONObject().apply {
            put("userId", userId)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.getRecommendation(requestBody)
    }

    companion object {
        @Volatile
        private var instance: RecommendationRepository? = null
        fun getInstance(
            apiService: ApiService
        ): RecommendationRepository =
            instance ?: synchronized(this) {
                instance ?: RecommendationRepository(apiService)
            }.also { instance = it }
    }
}
