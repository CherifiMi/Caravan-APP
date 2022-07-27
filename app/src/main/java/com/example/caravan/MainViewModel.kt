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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBuyersUseCase: GetBuyersUseCase,
    private val accountService: AccountService
): ViewModel(){

    val hasUser = accountService.hasUser()

    val isActivated = false

    val useType = "Buyer"

    private val _spalsh = MutableStateFlow(true)
    val spalsh = _spalsh.asStateFlow()

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