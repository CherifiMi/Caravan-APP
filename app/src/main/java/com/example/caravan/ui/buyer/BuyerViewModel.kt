package com.example.caravan.ui.buyer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.Product
import com.example.caravan.domain.model.ProductEntity
import com.example.caravan.domain.model.ProductsList
import com.example.caravan.domain.model.mokeCats
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.domain.use_cases.GetProductsUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val repository: CaravanRepository,
    private val accountService: AccountService
) : ViewModel() {

    //_____________________________value
    val thisImage = mutableStateOf(0)
    val buyOrAddToCartSheet = mutableStateOf(false)
    val loading = mutableStateOf(false)
    val catsPopUp = mutableStateOf(false)

    val savedData =
        Gson().fromJson(
            runBlocking(Dispatchers.IO) {

                repository.getSavedProductList()

            }.productList, ProductsList::class.java
        )

    val x: MutableState<List<Product>> = mutableStateOf(savedData)
    var selectedCat = mutableStateOf(-1)
    //_____________________________functions

    fun getCurrentProduct(s: String): Product {
        return savedData[s.toInt()]
    }

    fun resetUserList() {
        selectedCat.value = -1
        x.value = savedData
    }


    fun getProducts() {
        Log.d("TESTAPI", "loading")

        getProductsUseCase().onEach {
            when (it) {
                is Result.Loading -> {
                    Log.d("TESTAPI", "loading")
                }
                is Result.Success -> {
                    repository.saveProductList(it.data)
                    x.value = it.data!!
                    loading.value = true
                    Log.d("TESTAPI", it.data.toString())
                }
                is Result.Error -> {
                    loading.value = true
                    Log.d("TESTAPI", it.message.toString())
                }

            }
        }.launchIn(viewModelScope)
    }

    fun changeMyList() {
        x.value =
            savedData.filter { product: Product ->
                var b = false
                for (i in mokeCats.catList[selectedCat.value].subCats) {
                    if (product.cat.contains(i)) {
                        b = true
                        break
                    }
                }
                b
            }

    }

    fun selectedSubCat(item: String) {

        x.value =
            savedData.filter { product: Product ->
                var b = false
                for (i in product.cat) {
                    if (item == i) {
                        b = true
                        break
                    }
                }
                b
            }

        catsPopUp.value = false
    }

    fun search(s: String) {

        x.value =
            savedData.filter { product: Product ->

                product.name.contains(s)
            }


    }

}