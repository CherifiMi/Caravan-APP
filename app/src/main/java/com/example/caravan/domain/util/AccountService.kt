package com.example.caravan.domain.util

interface AccountService {
    fun createAnonymousAccount(onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
}