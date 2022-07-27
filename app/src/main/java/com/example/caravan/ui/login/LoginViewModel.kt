package com.example.caravan.ui.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.util.Constants.Companion.MIN_PASS_LENGTH
import com.example.caravan.data.util.Constants.Companion.PASS_PATTERN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
): ViewModel() {


    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun Login(){
        Log.d("TESTGMAIL", email.value + " " + password.value)
    }

    fun getuser(){
        Log.d("LOGINTEST",accountService.getUserId())
        //accountService.signOut()
        //accountService.hasUser()
    }


    private fun String.isValidEmail(): Boolean {
        return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun onError(error: Throwable) {
        Log.d("LOGINTEST", error.toString())
    }

    private val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    private fun linkWithEmail() {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.linkAccount(email.value, password.value) { error ->
                Log.d("LOGINTEST", error.toString())
            }
        }
    }

    private fun String.isValidPassword(): Boolean {
        return this.isNotBlank()
                && this.length >= MIN_PASS_LENGTH
                && Pattern.compile(PASS_PATTERN).matcher(this).matches()
    }

    fun onSignInClick() {
        if (!email.value.isValidEmail()) {
            Log.d("LOGINTEST", "email_error")
            return
        }
        if (password.value.isBlank() && password.value.isValidPassword()) {
            Log.d("LOGINTEST", "empty_password_error")
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.authenticate(email.value, password.value) { error ->
                if (error == null) {
                    linkWithEmail()
                } else onError(error)
            }
        }

    }
}