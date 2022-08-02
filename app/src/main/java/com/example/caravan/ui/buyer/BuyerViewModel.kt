package com.example.caravan.ui.buyer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.ProductsList
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.domain.use_cases.GetProductsUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val repository: CaravanRepository,
    private val accountService: AccountService
) : ViewModel() {

    fun signOut(navController: NavHostController){

        accountService.signOut()

        navController.navigate(Screens.Main.route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }

    val savedData =
        Gson().fromJson(
            runBlocking (Dispatchers.IO){
                repository.getSavedProductList()
            }.productList, ProductsList::class.java)


    fun getProducts() {
        Log.d("TESTAPI", "loading")

        getProductsUseCase().onEach {
            when (it) {
                is Result.Loading -> {
                    Log.d("TESTAPI", "loading")
                }
                is Result.Success -> {
                    repository.saveProductList(it.data)
                    Log.d("TESTAPI", it.data.toString())
                }
                is Result.Error -> {
                    Log.d("TESTAPI", it.message.toString())
                }

            }
        }.launchIn(viewModelScope)
    }

}