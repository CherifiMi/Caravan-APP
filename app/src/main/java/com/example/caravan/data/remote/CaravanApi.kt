package com.example.caravan.data.remote

import com.example.caravan.domain.model.Buyer
import com.example.caravan.domain.model.BuyersList
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.UserType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CaravanApi {

    @GET("buyers")
    suspend fun getBuyers(): BuyersList

    @POST("buyers")
    suspend fun postNewBuyer(@Body buyer: Buyer): ResponseBody

    @POST("type")
    suspend fun getUserType(@Body id: Id): UserType
}