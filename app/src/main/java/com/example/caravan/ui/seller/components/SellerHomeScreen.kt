package com.example.caravan.ui.seller

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.common.components.MyProductItem
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.theme.Typography
import com.google.gson.Gson

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SellerHomeScreen(
    navController: NavHostController,
    viewModel: SellerViewModel = hiltViewModel()
) {

    viewModel.signOut(navController)


    Column() {
        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "caravan",
                style = Typography.h1,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        })

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.alpha(if (!viewModel.loading.value) 0f else 1f),
                text = "is loading",
                style = Typography.h1,
                textAlign = TextAlign.Center
            )

            if (!viewModel.loading.value){
                if (viewModel.myProducts.isNullOrEmpty()) {

                    Text(
                        text = "no data",
                        style = Typography.h1,
                        textAlign = TextAlign.Center
                    )

                    // TODO: go to you have no products

                } else {

                    // TODO: go to list view
                    ProductsListScreen(viewModel, navController)
                }
            }



        }
    }
    viewModel.getSellerProducts()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsListScreen(viewModel: SellerViewModel, navController: NavHostController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2) ,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(10.dp)
    ) {

        itemsIndexed(items = viewModel.myProducts ?: listOf() ) { index, item ->
            MyProductItem(item){
                navController.navigate(Screens.ProductSeller.passItem(item = index.toString()))
            }
        }
    }
}