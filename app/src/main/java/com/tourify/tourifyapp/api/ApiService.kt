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
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @FormUrlEncoded
    @POST("/user/registration")
    suspend fun registration(
        @Field("token") token: String,
        @Field("email") email: String
    ): RegistrationResponse

    @FormUrlEncoded
    @POST("/user/verification")
    suspend fun verification(
        @Field("token") token: String,
        @Field("email") email: String,
        @Field("codeVerif") codeVerif: Int
    ): VerificationResponse

    @FormUrlEncoded
    @PUT("/user/password")
    suspend fun createPassword(
        @Field("token") token: String,
        @Field("password") password: String
    ): CreatePasswordResponse

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("token") token: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/user/logout")
    suspend fun logout(
        @Field("token") token: String,
        @Field("email") email: String
    ): LogoutResponse

    @FormUrlEncoded
    @GET("/destinations")
    suspend fun getDestinations(
        @Field("token") token: String
    ): DestinationsResponse

    @FormUrlEncoded
    @GET("/favorites")
    suspend fun getFavorites(
        @Field("token") token: String,
        @Field("userId") userId: Int
    ): FavoritesResponse

    @FormUrlEncoded
    @POST("/favorites")
    suspend fun addFavorites(
        @Field("token") token: String,
        @Field("userId") userId: Int,
        @Field("destinationsId") destinationsId: Int
    ): AddFavoritesResponse

    @FormUrlEncoded
    @DELETE("/favorites")
    suspend fun deleteFavorites(
        @Field("token") token: String,
        @Field("userId") userId: Int,
        @Field("destinationsId") destinationsId: Int
    ): DeleteFavoritesResponse

    @FormUrlEncoded
    @GET("/culinary")
    suspend fun getCulinary(
        @Field("token") token: String
    ): CulinaryResponse

    @FormUrlEncoded
    @POST("/booking")
    suspend fun bookingTrip(
        @Field("token") token: String,
        @Field("userId") userId: Int,
        @Field("destinationsId") destinationsId: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("noHp") noHp: String,
        @Field("tripDate") tripDate: String
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