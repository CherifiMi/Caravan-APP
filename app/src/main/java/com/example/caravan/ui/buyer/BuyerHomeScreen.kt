package com.example.caravan.ui.buyer


import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.R
import com.example.caravan.common.components.MyProductItem
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.theme.*
import com.example.caravan.ui.seller.SellerViewModel
import com.example.caravan.ui.signup.SignUpViewModel

@Composable
fun BuyerHomeScreen(
    navController: NavHostController,
    viewModel: BuyerViewModel = hiltViewModel()
) {

   viewModel.getProducts()

    Log.d("DBTEST", viewModel.savedData[1].id)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBarHeader()
        OurProductsWithSearch()
        Spacer(modifier = Modifier.padding(10.dp))
        ProductCategory()
        Spacer(modifier = Modifier.padding(10.dp))
        ProductGrid(viewModel, navController)
    }

}

@Composable
fun TopAppBarHeader() {
    Card(
        shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,) {
                        IconButton(onClick = { }) {
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


                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Backpack,
                            contentDescription = ""
                        )
                    }

                }
            })
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
                    modifier = Modifier.clickable{ },
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
            IconButton(onClick = { }) {
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
fun ProductCategory() {
    val itemList = listOf("Sneakers", "Jacket", "Watch", "Watch")
    val categoryImagesList = listOf<Int>(
        R.drawable.shoe_thumb_2,
        R.drawable.jacket,
        R.drawable.watch,
        R.drawable.watch
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(itemList.size) { item ->
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .border(
                        color = if (item == 0) orange else lightGray,
                        width = 2.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(categoryImagesList[item]),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp, 30.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 5.dp,
                                end = 16.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                        text = itemList[item],
                        color = if (item == 0) lightblack else Color.LightGray
                    )
                }

            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGrid(viewModel: BuyerViewModel, navController: NavHostController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2) ,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(8.dp)
    ) {

        itemsIndexed(items = viewModel.savedData ?: listOf() ) { index, item ->
            MyProductItem(item){
                navController.navigate(Screens.ProductSeller.passItem(item = index.toString()))
            }
        }
    }
}
