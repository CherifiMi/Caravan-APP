package com.example.caravan.data.remote

import com.example.caravan.domain.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CaravanApi {

    @POST("buyers")
    suspend fun postNewBuyer(@Body buyer: Buyer): ResponseBody

    @POST("sellers")
    suspend fun postNewSeller(@Body buyer: Seller): ResponseBody

    @POST("reps")
    suspend fun postNewRep(@Body buyer: Rep): ResponseBody

    @POST("type")
    suspend fun getUserType(@Body id: Id): UserType


    @POST("product/all")
    suspend fun getAllSellerProducts(@Body id: Id): ProductsList

    @GET("product")
    suspend fun getProducts(): ProductsList

    @POST("product")
    suspend fun createNewProduct(@Body product: Product): ResponseBody

    @PUT("product")
    suspend fun changeThisProduct(@Body product: Product): ResponseBody

    @DELETE("product")
    suspend fun deleteThisProduct(@Body id: Id): ResponseBody
}