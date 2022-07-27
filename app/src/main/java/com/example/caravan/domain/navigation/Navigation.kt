package com.example.pizzaorderapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.caravan.MainViewModel
import com.example.caravan.ui.login.LoginScreen
import com.example.caravan.ui.signup.screens.*


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
){

    NavHost(
        navController,
        startDestination =
        when(viewModel.firstScreen){
            "login" -> Screens.Login.route
            "wait" -> Screens.Wait.route
            "buyer" -> Screens.InfoBuyer.route
            "seller" -> Screens.InfoSeller.route
            "rep" -> Screens.InfoRep.route
            "nonet" -> Screens.Wait.route
            else -> Screens.Wait.route
        }
    ){

        composable(route = Screens.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.SelectUserType.route) {
            SelectUserScreen(navController = navController)
        }

        composable(route = Screens.InfoBuyer.route) {
            InfoBuyerScreen(navController = navController)
        }
        composable(route = Screens.InfoSeller.route) {
            InfoSellerScreen(navController = navController)
        }
        composable(route = Screens.InfoRep.route) {
            InfoRepScreen(navController = navController)
        }
        composable(route = Screens.Wait.route) {
            WaitForAdminScreen(navController = navController)
        }
    }

}

//if (viewModel.hasUser){
//    if (viewModel.isActivated){
//        when(viewModel.useType){
//            //"Buyer" -> BuyerHomeScreen()
//            //"Seller" -> SellerHomeScreen()
//            //else -> RepHomeScreen()
//        }
//    }
//    else{
//        WaitForAdminScreen()
//    }
//}else{
//    LoginScreen()
//}