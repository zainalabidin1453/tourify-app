package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.RegistrationResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CheckEmailRepository(private val apiService: ApiService) {
    suspend fun checkEmail(email: String): RegistrationResponse {
        val jsonObject = JSONObject().apply {
            put("email", email)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.registration(requestBody)
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
