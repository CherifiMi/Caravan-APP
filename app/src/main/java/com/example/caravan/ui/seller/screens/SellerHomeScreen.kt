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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.amplifyframework.core.Amplify
import com.example.caravan.common.components.MyProductItem
import com.example.caravan.common.components.MyTopBar
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SellerHomeScreen(
    navController: NavHostController,
    viewModel: SellerViewModel = hiltViewModel()
) {

    Scaffold(
        topBar =
        { MyTopBar(navController) },
        floatingActionButton =
        {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                backgroundColor = PinkRed,
                onClick = {
                    navController.navigate(Screens.ProductSeller.passItem(item = (-1).toString()))
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        Column() {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier.alpha(if (!viewModel.loading.value) 0f else 1f),
                    text = "is loading",
                    style = Typography.h1,
                    textAlign = TextAlign.Center
                )

                if (!viewModel.loading.value) {
                    if (viewModel.myProducts.value.isNullOrEmpty()) {

                        Text(
                            text = "there is no data",
                            style = Typography.h1,
                            textAlign = TextAlign.Center
                        )

                    } else {
                        ProductsListScreen(viewModel, navController)
                    }
                }


            }
        }
    }

    viewModel.getSellerProducts()
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsListScreen(viewModel: SellerViewModel, navController: NavHostController) {


    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(10.dp)
    ) {

        item (span = { GridItemSpan(2) }){
            Text(
                modifier = Modifier.padding(16.dp),
                text = "You have ${viewModel.myProducts.value?.size} product",
                style = Typography.h1
            )
        }

        itemsIndexed(items = viewModel.myProducts.value ?: listOf()) { index, item ->
            MyProductItem(item) {
                navController.navigate(Screens.ProductSeller.passItem(item = index.toString()))
            }
        }
    }
}