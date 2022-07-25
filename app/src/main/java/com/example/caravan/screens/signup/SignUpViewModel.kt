package com.example.caravan.screens.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor(): ViewModel(){

    var selected_type = mutableStateOf(1)

}