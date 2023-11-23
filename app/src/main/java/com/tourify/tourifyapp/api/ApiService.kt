package com.tourify.tourifyapp.api

import com.tourify.tourifyapp.model.CheckEmailResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("exec")
    suspend fun checkEmail(
        @Field("token") token: String,
        @Field("path") path: String,
        @Field("email") email: String
    ): CheckEmailResponse
}