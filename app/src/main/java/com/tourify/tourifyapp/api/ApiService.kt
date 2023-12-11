package com.tourify.tourifyapp.api

import com.tourify.tourifyapp.model.AddFavoritesResponse
import com.tourify.tourifyapp.model.BookingTripResponse
import com.tourify.tourifyapp.model.CreatePasswordResponse
import com.tourify.tourifyapp.model.CulinaryResponse
import com.tourify.tourifyapp.model.DeleteFavoritesResponse
import com.tourify.tourifyapp.model.DestinationsResponse
import com.tourify.tourifyapp.model.FavoritesResponse
import com.tourify.tourifyapp.model.LoginResponse
import com.tourify.tourifyapp.model.LogoutResponse
import com.tourify.tourifyapp.model.MyProfileResponse
import com.tourify.tourifyapp.model.MyTicketsResponse
import com.tourify.tourifyapp.model.RegistrationResponse
import com.tourify.tourifyapp.model.ScanningObjectResponse
import com.tourify.tourifyapp.model.VerificationResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("/user/registration")
    suspend fun registration(
        @Body email: String
    ): RegistrationResponse

    @POST("/user/verification")
    suspend fun verification(
        @Body email: String,
        @Body codeVerif: Int
    ): VerificationResponse

    @PUT("/user/password/{email}")
    suspend fun createPassword(
        @Path("email") email: String,
        @Body password: String
    ): CreatePasswordResponse

    @POST("/user/login")
    suspend fun login(
        @Body email: String,
        @Body password: String
    ): LoginResponse

    @POST("/user/logout")
    suspend fun logout(
        @Body email: String
    ): LogoutResponse

    @GET("/destinations")
    suspend fun getDestinations(): DestinationsResponse

    @PUT("/favorites/{userId}")
    suspend fun getFavorites(
        @Path("userId") userId: Int,
    ): FavoritesResponse

    @POST("/favorites")
    suspend fun addFavorites(
        @Body userId: Int,
        @Body destinationId: Int
    ): AddFavoritesResponse

    @DELETE("/favorites/{userId}/{destinationId}")
    suspend fun deleteFavorites(
        @Path("userId") userId: Int,
        @Path("destinationId") destinationId: Int
    ): DeleteFavoritesResponse

    @GET("/culinary")
    suspend fun getCulinary(): CulinaryResponse

    @POST("/booking")
    suspend fun bookingTrip(
        @Body userId: Int,
        @Body destinationId: Int,
        @Body tourGuideId: Int,
        @Body name: String,
        @Body email: String,
        @Body telephone: String,
        @Body tripDate: String,
        @Body note: String
    ): BookingTripResponse

    @FormUrlEncoded
    @GET("/mytickets")
    suspend fun getMyTickets(
        @Field("token") token: String,
        @Field("userId") userId: Int
    ): MyTicketsResponse

    @FormUrlEncoded
    @GET("/myprofile")
    suspend fun getMyProfile(
        @Field("token") token: String,
        @Field("userId") userId: Int
    ): MyProfileResponse

    @FormUrlEncoded
    @POST("/scanning")
    suspend fun scanningObject(
        @Field("token") token: String,
        @Field("photoObject") photoObject: String
    ): ScanningObjectResponse
}