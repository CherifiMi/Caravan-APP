package com.example.caravan.ui.buyer


import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.caravan.R
import com.example.caravan.common.components.MyProductItem
import com.example.caravan.domain.model.mokeCats
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun BuyerHomeScreen(
    navController: NavHostController,
    viewModel: BuyerViewModel = hiltViewModel()
) {

    runBlocking {
        viewModel.getProducts()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBarHeader() {
            viewModel.changeMyList()
            Log.d("HILLO", viewModel.x.toString())
        //viewModel.signOut(navController)
        }
        OurProductsWithSearch()
        Spacer(modifier = Modifier.padding(10.dp))
        ProductCategory(viewModel)
        Spacer(modifier = Modifier.padding(10.dp))
        ProductGrid(viewModel, navController)
    }

}

@Composable
fun TopAppBarHeader(function: () -> Unit) {

    TopAppBar(
        elevation = 2.dp,
        modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                IconButton(
                    onClick = { function() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = ""
                    )
                }
                Text(
                    text = "caravan",
                    style = Typography.h1,
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
            }


            IconButton(onClick = {  }) {
                Icon(
                    modifier = Modifier.padding(12.dp),
                    painter = painterResource(id = R.drawable.cart_emp),
                    contentDescription = ""
                )
            }

        }
    }

}

@Composable
fun OurProductsWithSearch() {
    var search by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 30.dp)
    ) {
        TextField(
            modifier = Modifier.scale(scaleY = 0.9F, scaleX = 1F),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = lightbox,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            value = search,
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            onValueChange = { search = it },
            placeholder = {
                Text(
                    text = "Search Products",
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable { },
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = lightblack
                )
            },
        )
        Spacer(modifier = Modifier.width(5.dp))
        Card(
            modifier = Modifier
                .width(64.dp)
                .padding(start = 16.dp)
                .clickable { },
            elevation = 5.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.filter_list),
                    contentDescription = "Filter Icon",
                    modifier = Modifier.size(20.dp, 20.dp)
                )

            }
        }

    }


}

@Composable
fun ProductCategory(viewModel: BuyerViewModel) {
    val itemList = mokeCats.catList

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(
                rememberScrollState()
            )
    ) {
        Spacer(modifier = Modifier.padding(8.dp))

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                           viewModel.resetUserList()
                },
            border = BorderStroke(2.dp, if (viewModel.selectedCat.value == -1) PinkRed else LightGrey)
        ) {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(24.dp)
            )

            Text(
                modifier = Modifier.padding(16.dp, 8.dp),
                text = "All",
                style = Typography.h3
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        itemList.forEachIndexed{index, i ->

            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        viewModel.selectedCat.value = index
                        viewModel.changeMyList()
                    },
                border = BorderStroke(2.dp, if (viewModel.selectedCat.value == index) PinkRed else LightGrey)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            .size(24.dp),
                        model = i.picUrl,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(16.dp, 8.dp),
                        text = i.name,
                        style = Typography.h3
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGrid(viewModel: BuyerViewModel, navController: NavHostController) {



    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(8.dp)
    ) {

        itemsIndexed(items = viewModel.x.value) { index, item ->
            MyProductItem(item, true) {
                navController.navigate(Screens.ProductBuyer.passItem(index = index.toString()))
            }
        }
    }
}

