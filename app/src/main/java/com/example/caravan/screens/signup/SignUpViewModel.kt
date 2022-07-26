package com.example.caravan.screens.signup

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor(): ViewModel(){

    var selected_type = mutableStateOf(1)

    var selectedText = mutableStateOf("")
    var expanded = mutableStateOf(false)

    var gmail = mutableStateOf("")
    var password = mutableStateOf("")

    var first_name = mutableStateOf("")
    var last_name = mutableStateOf("")
    var brand_name = mutableStateOf("")
    var address = mutableStateOf("")
    var phone = mutableStateOf("")
}