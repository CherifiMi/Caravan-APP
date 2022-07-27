package com.example.caravan.data.repository

import com.example.caravan.data.remote.CaravanApi
import com.example.caravan.domain.model.Buyer
import com.example.caravan.domain.model.BuyersList
import com.example.caravan.domain.model.UserType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject

class CaravanRepository @Inject constructor(private val caravanApi: CaravanApi){
    suspend fun getBuyers(): BuyersList{
        return caravanApi.getBuyers()
    }

    suspend fun postNewBuyer(buyer: Buyer): ResponseBody{
        return  caravanApi.postNewBuyer(buyer)
    }

    suspend fun getUserType(): UserType{
        return caravanApi.getUserType()
    }
}