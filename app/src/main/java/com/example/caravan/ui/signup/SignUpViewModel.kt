package com.example.caravan.ui.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caravan.data.repository.AccountService
import com.example.caravan.domain.ext.*
import com.example.caravan.domain.model.Buyer
import com.example.caravan.domain.model.Rep
import com.example.caravan.domain.model.Seller
import com.example.caravan.domain.use_cases.PostNewBuyerUseCase
import com.example.caravan.domain.use_cases.PostNewRepUseCase
import com.example.caravan.domain.use_cases.PostNewSellerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val postNewBuyerUseCase: PostNewBuyerUseCase,
    private val postNewSellerUseCase: PostNewSellerUseCase,
    private val postNewRepUseCase: PostNewRepUseCase
) : ViewModel() {

    var selected_type = mutableStateOf(1)

    var selectedText = mutableStateOf("")
    var expanded = mutableStateOf(false)

    var email = mutableStateOf("")
    var password = mutableStateOf("")

    var first_name = mutableStateOf("")
    var last_name = mutableStateOf("")
    var brand_name = mutableStateOf("")
    var address = mutableStateOf("")
    var phone = mutableStateOf("")


    fun CreateNewUser(i: Int) {

        val isBuyerDataValid =
            i == 1 && first_name.value.isNotEmpty() && last_name.value.isNotEmpty() && brand_name.value.isNotEmpty() && address.value.isNotEmpty() && phone.value.isNotEmpty()

        val isSellerDataValid =
            i == 2 && first_name.value.isNotEmpty() && last_name.value.isNotEmpty() && brand_name.value.isNotEmpty() &&  phone.value.isNotEmpty() && selectedText.value.isNotEmpty()

        val isRepDataValid =
            i == 3 && first_name.value.isNotEmpty() && last_name.value.isNotEmpty() && phone.value.isNotEmpty()

        if (
           !isBuyerDataValid && !isSellerDataValid && !isRepDataValid
        ){
            Log.d("TESTGMAIL", "please fill all data")
            return
        }

        if (!email.value.isValidEmail()) {
            Log.d("TESTGMAIL", "email_error")
            return
        }

        if (!password.value.isValidPassword()) {
            Log.d("TESTGMAIL", "password_error")
            return
        }


        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.createAccount(email.value, password.value) { error ->
                if (error == null) {
                    linkWithEmail()
                } else onError(error)
            }
        }
    }

    private fun linkWithEmail() {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.linkAccount(email.value, password.value) { error ->
                if (error != null) {

                    viewModelScope.launch {
                        when (selected_type.value) {
                            1 -> postNewBuyerUseCase(
                                Buyer(
                                    id = null,
                                    address = address.value,
                                    autheId = accountService.getUserId(),
                                    brand = brand_name.value,
                                    isActive = false,
                                    owner = "${first_name.value} ${last_name.value}",
                                    phone = phone.value
                                )
                            )
                            2 -> postNewSellerUseCase(
                                Seller(
                                    autheId = accountService.getUserId(),
                                    brand = brand_name.value,
                                    id = null,
                                    isActive = false,
                                    ordersId = null,
                                    owner = "${first_name.value} ${last_name.value}",
                                    phone = phone.value,
                                    productsId = null,
                                    type = selectedText.value
                                )
                            )

                            3 -> postNewRepUseCase(
                                Rep(
                                    autheId = accountService.getUserId(),
                                    id = null,
                                    isActive = false,
                                    myBuyers = null,
                                    mySellers = null,
                                    name = "${first_name.value} ${last_name.value}",
                                    phone = phone.value,
                                )
                            )
                        }
                        try {


                        } catch (e: Exception) {
                            Log.d("TESTGMAIL", e.toString())
                        }
                        Log.d("TESTGMAIL", error.message.toString())
                    }

                } else {
                    Log.d("TESTGMAIL", "this account already exist")
                }
            }
        }
    }

}