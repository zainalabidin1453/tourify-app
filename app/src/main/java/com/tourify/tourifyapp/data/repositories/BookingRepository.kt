package com.tourify.tourifyapp.data.repositories

import com.tourify.tourifyapp.api.ApiService
import com.tourify.tourifyapp.model.BookingTripResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class BookingRepository(private val apiService: ApiService) {
    suspend fun bookingTrip(
        userId: String,
        destinationId: String,
        tourGuideId: String,
        name: String,
        email: String,
        telephone: String,
        tripDate: String,
        note: String
    ): BookingTripResponse {
        val jsonObject = JSONObject().apply {
            put("userId", userId)
            put("destinationId", destinationId)
            put("tourGuideId", tourGuideId)
            put("name", name)
            put("email", email)
            put("telephone", telephone)
            put("tripDate", tripDate)
            put("note", note)
        }
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.bookingTrip(requestBody)
    }

    companion object {
        @Volatile
        private var instance: BookingRepository? = null
        fun getInstance(
            apiService: ApiService
        ): BookingRepository =
            instance ?: synchronized(this) {
                instance ?: BookingRepository(apiService)
            }.also { instance = it }
    }
}
