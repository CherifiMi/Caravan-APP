package com.example.caravan.data.repository

import android.net.Uri
import android.util.Log
import com.amplifyframework.core.Amplify
import com.example.caravan.data.local.CaravanDao
import com.example.caravan.data.remote.CaravanApi
import com.example.caravan.domain.model.*
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody
import javax.inject.Inject

class CaravanRepository @Inject constructor(
    private val caravanApi: CaravanApi,
    private val dao: CaravanDao
    ){
    suspend fun getProducts(): ProductsList{
        return caravanApi.getProducts()
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

    suspend fun getAllSellerProducts(id: Id): ProductsList {
        return caravanApi.getAllSellerProducts(id)
    }

    suspend fun createNewProduct(product: Product): ResponseBody{
        return  caravanApi.createNewProduct(product)
    }
    suspend fun changeThisProduct(product: Product): ResponseBody{
        return  caravanApi.changeThisProduct(product)
    }
    suspend fun deleteThisProduct(id: Id): ResponseBody{
        return  caravanApi.deleteThisProduct(id)
    }

    //_________________________db

    suspend fun saveProductList(productsList: ProductsList?){
        dao.deleteAll()
        val gson = Gson().toJson(productsList)
        dao.saveAllProducts(ProductEntity(productList = gson))
    }
    suspend fun getSavedProductList(): ProductEntity {
        return dao.getAllProducts()
    }


}