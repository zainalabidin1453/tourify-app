package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.LoginResponse
import com.tourify.tourifyapp.model.LogoutResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LoginRepository(private val apiService: ApiService) {
    suspend fun login(email: String, password: String): LoginResponse {
        val jsonObject = JSONObject().apply {
            put("email", email)
            put("password", password)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.login(requestBody)
    }

    suspend fun logout(email: String): LogoutResponse {
        val jsonObject = JSONObject().apply {
            put("email", email)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.logout(requestBody)
    }

    companion object {
        @Volatile
        private var instance: LoginRepository? = null
        fun getInstance(
            apiService: ApiService
        ): LoginRepository =
            instance ?: synchronized(this) {
                instance ?: LoginRepository(apiService)
            }.also { instance = it }
    }
}
