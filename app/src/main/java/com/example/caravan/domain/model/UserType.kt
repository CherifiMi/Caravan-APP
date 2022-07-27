package com.example.caravan.domain.model


import com.google.gson.annotations.SerializedName

data class UserType(
    @SerializedName("autheId")
    val autheId: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("type")
    val type: String
)