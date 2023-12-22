package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.StatusPesananResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class StatusPesananRepository(private val apiService: ApiService) {
    suspend fun statusPesanan(bookingCode: String): StatusPesananResponse {
        val jsonObject = JSONObject().apply {
            put("bookingCode", bookingCode)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.statusPesanan(requestBody)
    }

    companion object {
        @Volatile
        private var instance: StatusPesananRepository? = null
        fun getInstance(
            apiService: ApiService
        ): StatusPesananRepository =
            instance ?: synchronized(this) {
                instance ?: StatusPesananRepository(apiService)
            }.also { instance = it }
    }
}
