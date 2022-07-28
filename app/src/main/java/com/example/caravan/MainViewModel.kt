package com.example.caravan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.UserType
import com.example.caravan.domain.use_cases.GetBuyersUseCase
import com.example.caravan.domain.use_cases.GetUserTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBuyersUseCase: GetBuyersUseCase,
    private val getUserTypeUseCase: GetUserTypeUseCase,
    private val accountService: AccountService
) : ViewModel() {

    //__________________________values
    private val _spalsh = MutableStateFlow(true)
    val spalsh = _spalsh.asStateFlow()

    val there_is_net = true
    var firstScreen = ""


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
        Log.d("TEST_SIGN", accountService.getUserId())
        return getUserTypeUseCase(
            Id(
                id = accountService.getUserId()
            )
        )
    }

    fun testCall() {
        getBuyersUseCase().onEach {
            when (it) {
                is Result.Loading -> {
                    Log.d("TESTAPI", "loading")
                }
                is Result.Success -> {
                    _spalsh.value = false
                    Log.d("TESTAPI", it.data.toString())
                }
                is Result.Error -> {
                    _spalsh.value = false
                    Log.d("TESTAPI", it.message.toString())
                }

            }
        }.launchIn(viewModelScope)
    }

}