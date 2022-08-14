package com.example.caravan.data.repository

import com.example.caravan.data.local.CaravanDao
import com.example.caravan.data.remote.CaravanApi
import com.example.caravan.domain.model.*
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun getSellerByKey(id: Id): ResponseBody{
        return caravanApi.getSellerByKey(id)
    }

    suspend fun getBuyerByKey(id: Id): ResponseBody{
        return caravanApi.getBuyerByKey(id)
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

    suspend fun getCats(): ResponseBody{
        return caravanApi.getCats()
    }

    suspend fun paymentIntent(amount: Int, linked: String, cur: String): String {
        var requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("amount", amount.toString())
            .addFormDataPart("currency", cur)
            .addFormDataPart("linked", linked)
            .build()

        return caravanApi.paymentIntent(requestBody).string()
    }

    suspend fun deleteOrder(id: Id){
        return  caravanApi.deleteOrder(id)
    }
    suspend fun makeOrder(order: Order){
        return  caravanApi.makeOrder(order)
    }
    suspend fun myOrder(id: Id): ResponseBody {
        return  caravanApi.myOrder(id)
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


    suspend fun saveUser(user: String){
        dao.deleteUser()
        dao.saveUser(UserEntity(user = user))
    }
    suspend fun getSavedUser(): UserEntity {
        return dao.getUser()
    }



    suspend fun saveCats(cats: String){
        dao.deleteCats()
        dao.saveCats(CatsEntity(cat = cats))
    }
    suspend fun getSavedCats(): CatsEntity {
        return dao.getCats()
    }



    suspend fun saveItem(product: Product, am: Int){
        dao.deleteItem()
        dao.saveItem(ProductItemEntity(product = Gson().toJson(product), amount = am))
    }
    suspend fun getSavedItem(): OrderItem {
        return OrderItem(
            amount = dao.getItem().amount,
            product = Gson().fromJson(dao.getItem().product, Product::class.java)
        )
    }

}