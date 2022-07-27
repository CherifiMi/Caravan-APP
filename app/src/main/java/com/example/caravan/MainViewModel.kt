package com.example.caravan

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.util.Result
import com.example.caravan.domain.use_cases.GetBuyersUseCase
import com.example.caravan.domain.use_cases.GetUserTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBuyersUseCase: GetBuyersUseCase,
    private val getUserTypeUseCase: GetUserTypeUseCase,
    private val accountService: AccountService
): ViewModel(){


    private val _spalsh = MutableStateFlow(false)
    val spalsh = _spalsh.asStateFlow()


    fun onSplashScreen(){
        viewModelScope.launch {
            getUserType()
            getIsUserActivated()
            startScreen()
        }
        _spalsh.value = true
    }

    private var isActivated = false
    private var userType = "buyer"

    var firstScreen = "login"

    fun startScreen(){
        if (true /*net*/){
            if (!accountService.hasUser()){
                firstScreen = "login"
            }else{
                if (!isActivated){
                    firstScreen = "wait"
                }else{
                    firstScreen = userType
                }
            }
        }else{
            firstScreen = "nonet"
        }
    }

    fun getUserType(){
        viewModelScope.launch(Dispatchers.Main) {
            userType = getUserTypeUseCase().type
        }

    }

    fun getIsUserActivated(){
        when(userType){
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

        isActivated = true
    }

    fun testCall(){
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