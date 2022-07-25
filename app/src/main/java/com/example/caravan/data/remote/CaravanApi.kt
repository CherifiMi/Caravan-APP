package com.example.caravan.data.remote

import com.example.caravan.domain.model.BuyersList
import retrofit2.http.GET

interface CaravanApi {

    @GET("buyers")
    suspend fun getBuyers(): BuyersList
}