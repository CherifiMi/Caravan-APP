package com.example.caravan.domain.navigation

import android.content.ContentResolver
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.caravan.MainApp
import com.example.caravan.MainViewModel
import com.example.caravan.ui.buyer.screens.BuyerHomeScreen
import com.example.caravan.ui.buyer.screens.BuyerProductScreen
import com.example.caravan.ui.errors.NoNetScreen
import com.example.caravan.ui.errors.SomethingWrongScreen
import com.example.caravan.ui.login.LoginScreen
import com.example.caravan.ui.rep.RepHomeScreen
import com.example.caravan.ui.seller.SellerHomeScreen
import com.example.caravan.ui.seller.screens.SellerEditProductScreen
import com.example.caravan.ui.signup.screens.*
import com.stripe.android.payments.paymentlauncher.PaymentLauncher


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMotionApi::class)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    cn: ContentResolver,
    paymentLauncher: PaymentLauncher,
    userId: String,
) {

    NavHost(
        navController,
        startDestination =
        when (viewModel.firstScreen) {
            "login" -> Screens.Login.route
            "wait" -> Screens.Wait.route
            "buyer" -> Screens.HomeBuyer.route
            "seller" -> Screens.HomeSeller.route
            "rep" -> Screens.HomeRep.route
            "nonet" -> Screens.NoNetError.route
            else -> Screens.SWError.route
        }
    ) {

        composable(
            route = Screens.Main.route,
            arguments = listOf(navArgument(name = "userId"){type = NavType.StringType})
        ) {
            var userId = it.arguments
            MainApp(viewModel, cn, paymentLauncher, args = userId)
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
            BuyerHomeScreen(navController = navController,userId = userId)
        }
        composable(
            route = Screens.ProductBuyer.route,
            arguments = listOf(navArgument(name = "index"){type = NavType.StringType})
        ) {
            var index = it.arguments
            BuyerProductScreen(navController = navController, args = index, paymentLauncher = paymentLauncher)
        }


        composable(route = Screens.HomeSeller.route) {
            SellerHomeScreen(mainNavController = navController,userId = userId)
        }
        composable(
            route = Screens.ProductSeller.route,
            arguments = listOf(navArgument(name = "item"){type = NavType.StringType})
        ) {
            var item = it.arguments
            SellerEditProductScreen(navController = navController, cn = cn, args = item, userId = userId)
        }


        composable(route = Screens.HomeRep.route) {
            RepHomeScreen(mainNavController = navController)
        }

        composable(route = Screens.SWError.route) {
            SomethingWrongScreen(navController = navController)
        }
        composable(route = Screens.NoNetError.route) {
            NoNetScreen(navController = navController)
        }
    }

}



