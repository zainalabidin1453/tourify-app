package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.VerificationResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class VerificationCodeRepository(private val apiService: ApiService) {
    suspend fun verificationCode(email: String, codeVerif: String): VerificationResponse {
        val jsonObject = JSONObject().apply {
            put("email", email)
            put("codeVerif", codeVerif)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.verification(requestBody)
    }

    companion object {
        @Volatile
        private var instance: VerificationCodeRepository? = null
        fun getInstance(
            apiService: ApiService
        ): VerificationCodeRepository =
            instance ?: synchronized(this) {
                instance ?: VerificationCodeRepository(apiService)
            }.also { instance = it }
    }
}
