package com.example.caravan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.Product
import com.example.caravan.domain.model.ProductsList
import com.example.caravan.domain.use_cases.GetProductsUseCase
import com.example.caravan.domain.use_cases.GetUserTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getUserTypeUseCase: GetUserTypeUseCase,
    private val accountService: AccountService,
    private val repository: CaravanRepository
) : ViewModel() {

    //__________________________values
    private val _spalsh = MutableStateFlow(true)
    val spalsh = _spalsh.asStateFlow()

    val there_is_net = true
    var firstScreen = ""

    val fakeProduct = Product(
        id = "gdf",
        name = "gsd",
        content = "sg",
        initPrice = 3,
        newPrice =3,
        minOrder = "sg",
        imageUrls = listOf("dsf"),
        cat = listOf("sdf"),
        sellerKey = "sgd"
    )


    //___________________________functions
    fun onSplashScreen() {

        if (there_is_net) {
            if (!accountService.hasUser()) {
                firstScreen = "login"
            } else {
                val usertype = getUserType()
                Log.d("TESTAPI", usertype)
                if (!getIsUserActivated(usertype)) {
                    firstScreen = "wait"
                } else {
                    firstScreen = usertype
                }
            }
        }else {
            firstScreen = "nonet"
        }



        _spalsh.value = false


    }




    fun getIsUserActivated(usertype: String): Boolean{
        when(usertype){
            "buyer"->{

                runBlocking {
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
                // TODO: get buyer by auth key
            }
            "seller"->{
                // TODO: get seller by auth key
            }
            else->{
                // TODO: get rep by auth key
            }
        }

        return true
    }

    fun getUserType(): String{

        try {
            return getUserTypeUseCase(Id(id = accountService.getUserId()))
        }catch (e: Exception){
           return getUserType()
        }
    }



}