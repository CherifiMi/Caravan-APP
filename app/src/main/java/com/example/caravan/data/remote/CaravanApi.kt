package com.example.caravan.data.remote

import com.example.caravan.domain.model.*
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

    @POST("sellers")
    suspend fun postNewSeller(@Body buyer: Seller): ResponseBody

    @POST("reps")
    suspend fun postNewRep(@Body buyer: Rep): ResponseBody

    @POST("type")
    suspend fun getUserType(@Body id: Id): UserType
}