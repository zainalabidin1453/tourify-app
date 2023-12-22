package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.LoginResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CreatePasswordRepository(private val apiService: ApiService) {
    suspend fun createPassword(email: String, password: String): LoginResponse {
        val jsonObject = JSONObject().apply {
            put("password", password)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.createPassword(email, requestBody)
    }

    companion object {
        @Volatile
        private var instance: CreatePasswordRepository? = null
        fun getInstance(
            apiService: ApiService
        ): CreatePasswordRepository =
            instance ?: synchronized(this) {
                instance ?: CreatePasswordRepository(apiService)
            }.also { instance = it }
    }
}
