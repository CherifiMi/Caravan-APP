package com.example.caravan

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.caravan.common.CaravanAppState
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.*
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.domain.use_cases.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getUserTypeUseCase: GetUserTypeUseCase,
    private val getSellerByKeyUseCase: GetSellerByKeyUseCase,
    private val getBuyerByKeyUseCase: GetBuyerByKeyUseCase,
    private val getRepByKeyUseCase: GetRepByKeyUseCase,
    private val accountService: AccountService,
    private val getCatsUseCase: GetCatsUseCase,
    private val repository: CaravanRepository
) : ViewModel() {

    //__________________________values
    private val _spalsh = MutableStateFlow(true)
    val spalsh = _spalsh.asStateFlow()

    val there_is_net = mutableStateOf(true)
    var firstScreen = ""

    //___________________________functions

    fun onSplashScreen() {
        if (there_is_net.value) {
            if (!accountService.hasUser()) {
                firstScreen = "login"
            } else {
                val usertype = getUserType()
                Log.d("TESTAPI", usertype)
                if (!getIsUserActivated(usertype)) {
                    firstScreen = "wait"
                } else {
                    getCats()
                    firstScreen = usertype
                }
            }
        } else {
            firstScreen = "nonet"
        }

        _spalsh.value = false
    }

    private fun getCats() {

        viewModelScope.launch(Dispatchers.IO) {

            getCatsUseCase().collectLatest { response ->
                response.data?.string()?.let { repository.saveCats(it) }
            }
        }

    }


    fun signOut(navController: NavHostController) {

        accountService.signOut()

        navController.navigate(Screens.Main.route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }


    fun getIsUserActivated(usertype: String): Boolean {
        when (usertype) {
            "buyer" -> {
                runBlocking {
                    getProductsUseCase().onEach {
                        when (it) {
                            is Result.Loading -> {
                                Log.d("TESTAPI", "loading")
                            }
                            is Result.Success -> {
                                Log.d("TESTAPI", it.data.toString())
                                return@onEach
                            }
                            is Result.Error -> {
                                Log.d("TESTAPI", it.message.toString())
                            }

                        }
                    }.collectLatest { repository.saveProductList(it.data) }
                }


                val job: Boolean = runBlocking {
                    async {
                        getBuyerByKeyUseCase(Id(id = accountService.getUserId())).collectLatest { respone ->
                            respone.data?.string()?.let {
                                Log.d("Mito", it)
                                repository.saveUser(it)
                            }
                        }
                    }.await()

                    val buyer =
                        Gson().fromJson(
                            repository.getSavedUser().user,
                            BuyersList::class.java
                        )[0]
                    buyer.isActive

                }

                return job
            }
            "seller" -> {

                val job: Boolean = runBlocking {
                    async {
                        getSellerByKeyUseCase(Id(id = accountService.getUserId())).collectLatest { respone ->
                            respone.data?.string()?.let {
                                Log.d("Mito", it)
                                repository.saveUser(it)
                            }
                        }
                    }.await()

                    val seller =
                        Gson().fromJson(
                            repository.getSavedUser().user,
                            SellerList::class.java
                        )[0]

                    seller.isActive

                }

                return job
            }
            else -> {
                val job: Boolean = runBlocking {
                    async {
                        getRepByKeyUseCase(Id(id = accountService.getUserId())).collectLatest { respone ->
                            respone.data?.string()?.let {
                                Log.d("Mito", it)
                                repository.saveUser(it)
                            }
                        }
                    }.await()

                    val rep =
                        Gson().fromJson(
                            repository.getSavedUser().user,
                            RepList::class.java
                        )[0]
                    rep.isActive

                }

                return job
            }
        }

        return false
    }

    fun getUserType(): String {

        try {
            return getUserTypeUseCase(Id(id = accountService.getUserId()))
        } catch (e: Exception) {
            return getUserType()
        }
    }


}

