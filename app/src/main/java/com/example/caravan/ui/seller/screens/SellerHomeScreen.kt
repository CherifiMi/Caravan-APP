package com.example.caravan.ui.seller

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amplifyframework.core.Amplify
import com.example.caravan.R
import com.example.caravan.common.components.MyProductItem
import com.example.caravan.common.components.MyTopBar
import com.example.caravan.common.components.SideMenu
import com.example.caravan.domain.model.BottomNavItem
import com.example.caravan.domain.navigation.Navigation
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography
import com.example.caravan.ui.seller.components.BottomNavigationBar
import com.example.caravan.ui.seller.screens.SellerOrdersScreen
import com.example.caravan.ui.seller.screens.SellerProductsScreen
import com.google.gson.Gson
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SellerHomeScreen(
    mainNavController: NavHostController,
    viewModel: SellerViewModel = hiltViewModel()
) {

    val navController = rememberNavController()

    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar =
        {
            MyTopBar(
                navController = navController,
                humClicked = {
                    scope.launch {
                        state.drawerState.open()
                    }
                }
            )
        },
        drawerContent = { SideMenu(mainNavController) },
        scaffoldState = state,
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "My Orders",
                        route = Screens.OrdersSeller.route,
                        icon = painterResource(id = R.drawable.order),
                    ),
                    BottomNavItem(
                        name = "My Products",
                        route = Screens.ProductsListSeller.route,
                        icon = painterResource(id = R.drawable.bag),
                    )
                ),
                navController = navController,
            ){
                navController.navigate(it.route)
            }
        }
    ) {

        NavHost(
            navController,
            startDestination = Screens.OrdersSeller.route
        ) {
            composable(route = Screens.OrdersSeller.route) {
                SellerOrdersScreen(navController = mainNavController)
            }
            composable(route = Screens.ProductsListSeller.route) {
                SellerProductsScreen(navController = mainNavController)
            }
        }

    }
}



