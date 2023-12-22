package com.tourify.tourifyapp.api

import com.tourify.tourifyapp.model.AddFavoritesResponse
import com.tourify.tourifyapp.model.BookingTripResponse
import com.tourify.tourifyapp.model.CulinaryResponse
import com.tourify.tourifyapp.model.DeleteFavoritesResponse
import com.tourify.tourifyapp.model.DestinationsResponse
import com.tourify.tourifyapp.model.FavoritesResponse
import com.tourify.tourifyapp.model.LoginResponse
import com.tourify.tourifyapp.model.LogoutResponse
import com.tourify.tourifyapp.model.MyProfileResponse
import com.tourify.tourifyapp.model.MyTicketsResponse
import com.tourify.tourifyapp.model.RecommendationResponse
import com.tourify.tourifyapp.model.RegistrationResponse
import com.tourify.tourifyapp.model.ScanningObjectResponse
import com.tourify.tourifyapp.model.StatusPesananResponse
import com.tourify.tourifyapp.model.VerificationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("user/registration")
    suspend fun registration(
        @Body requestBody: RequestBody
    ): RegistrationResponse

    @POST("user/verification")
    suspend fun verification(
        @Body requestBody: RequestBody
    ): VerificationResponse

    @PUT("user/password/{email}")
    suspend fun createPassword(
        @Path("email") email: String,
        @Body requestBody: RequestBody
    ): LoginResponse

    @POST("user/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): LoginResponse

    @POST("user/logout")
    suspend fun logout(
        @Body requestBody: RequestBody
    ): LogoutResponse

    @GET("destinations")
    suspend fun getDestinations(): DestinationsResponse

    @GET("favorites/{userId}")
    suspend fun getFavorites(
        @Path("userId") userId: Int,
    ): FavoritesResponse

    @POST("favorites")
    suspend fun addFavorites(
        @Body requestBody: RequestBody
    ): AddFavoritesResponse

    @DELETE("favorites")
    suspend fun deleteFavorites(
        @Body requestBody: RequestBody
    ): DeleteFavoritesResponse

    @GET("culinary")
    suspend fun getCulinary(): CulinaryResponse

    @POST("booking")
    suspend fun bookingTrip(
        @Body requestBody: RequestBody
    ): BookingTripResponse

    @Multipart
    @POST("scanning")
    suspend fun scanningObject(
        @Part image: MultipartBody.Part,
    ): ScanningObjectResponse

    @POST("predict")
    suspend fun getRecommendation(
        @Body requestBody: RequestBody
    ): RecommendationResponse

    @GET("mytickets/{userId}")
    suspend fun getMyTickets(
        @Path("userId") userId: Int,
    ): MyTicketsResponse

    @POST("booking_status")
    suspend fun statusPesanan(
        @Body requestBody: RequestBody
    ): StatusPesananResponse

    @GET("user/{userId}")
    suspend fun getMyProfile(
        @Path("userId") userId: Int,
    ): MyProfileResponse
}