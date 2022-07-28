package com.example.caravan.data.repository

import com.example.caravan.data.remote.CaravanApi
import com.example.caravan.domain.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class CaravanRepository @Inject constructor(private val caravanApi: CaravanApi){
    suspend fun getBuyers(): BuyersList{
        return caravanApi.getBuyers()
    }

    suspend fun postNewBuyer(buyer: Buyer): ResponseBody{
        return  caravanApi.postNewBuyer(buyer)
    }

    suspend fun postNewSeller(seller: Seller): ResponseBody{
        return  caravanApi.postNewSeller(seller)
    }

    suspend fun postNewRep(rep: Rep): ResponseBody{
        return  caravanApi.postNewRep(rep)
    }

    suspend fun getUserType(id: Id): UserType {
        return caravanApi.getUserType(id)
    }
}