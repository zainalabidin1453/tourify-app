package com.tourify.tourifyapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckEmailResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("codeVerif")
    val codeVerif: Int
) : Parcelable

