package com.example.caravan.domain.model


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("cat")
    val cat: List<String>,
    @SerializedName("content")
    val content: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrls")
    val imageUrls: List<String>,
    @SerializedName("initPrice")
    val initPrice: Int,
    @SerializedName("minOrder")
    val minOrder: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("newPrice")
    val newPrice: Int,
    @SerializedName("sellerKey")
    val sellerKey: String
)

class ProductsList : ArrayList<Product>()