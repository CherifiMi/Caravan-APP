package com.example.caravan.data.repository

import com.example.caravan.data.remote.CaravanApi
import com.example.caravan.domain.model.Buyer
import com.example.caravan.domain.model.BuyersList
import javax.inject.Inject

class CaravanRepository @Inject constructor(private val caravanApi: CaravanApi){
    suspend fun getBuyers(): BuyersList{
        return caravanApi.getBuyers()
    }
}