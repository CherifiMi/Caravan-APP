package com.example.caravan.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class LoginViewModel @Inject constructor(): ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun Login(){
        Log.d("TESTGMAIL", email.value + " " + password.value)
    }
}