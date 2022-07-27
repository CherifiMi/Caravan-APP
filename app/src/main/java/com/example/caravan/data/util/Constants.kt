package com.example.caravan.data.util

class Constants {
    companion object{
        const val BASE_URL = "https://caravan-ap1.herokuapp.com/v1/"

        const val MIN_PASS_LENGTH = 6
        const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"
    }
}