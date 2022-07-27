package com.example.pizzaorderapp.navigation


sealed class Screens(val route: String) {
    object Login: Screens(route = "login_screen")
    object InfoBuyer: Screens(route = "info_buyer_screen")
    object InfoSeller: Screens(route = "info_seller_screen")
    object InfoRep: Screens(route = "info_rep_screen")
    object SelectUserType: Screens(route = "select_screen")
    object Wait: Screens(route = "wait_screen")
}