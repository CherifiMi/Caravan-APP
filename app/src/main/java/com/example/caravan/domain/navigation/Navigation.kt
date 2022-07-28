package com.example.caravan.domain.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.caravan.MainApp
import com.example.caravan.MainViewModel
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.ui.buyer.BuyerHomeScreen
import com.example.caravan.ui.errors.NoNetScreen
import com.example.caravan.ui.errors.SomethingWrongScreen
import com.example.caravan.ui.login.LoginScreen
import com.example.caravan.ui.rep.RepHomeScreen
import com.example.caravan.ui.seller.SellerHomeScreen
import com.example.caravan.ui.signup.screens.*


@OptIn(ExperimentalMotionApi::class)
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
            "buyer" -> Screens.HomeBuyer.route
            "seller" -> Screens.HomeSeller.route
            "rep" -> Screens.HomeRep.route
            "nonet" -> Screens.NoNetError.route
            else -> Screens.SWError.route
        }
    ){

        composable(route = Screens.Main.route) {
            MainApp(viewModel)
        }

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


        composable(route = Screens.HomeBuyer.route) {
            BuyerHomeScreen(navController = navController)
        }
        composable(route = Screens.HomeSeller.route) {
            SellerHomeScreen(navController = navController)
        }
        composable(route = Screens.HomeRep.route) {
            RepHomeScreen(navController = navController)
        }

        composable(route = Screens.SWError.route) {
            SomethingWrongScreen(navController = navController)
        }
        composable(route = Screens.NoNetError.route) {
            NoNetScreen(navController = navController)
        }
    }

}

