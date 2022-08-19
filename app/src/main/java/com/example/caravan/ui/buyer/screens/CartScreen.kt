package com.example.caravan.ui.buyer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.caravan.R
import com.example.caravan.common.components.MyButton
import com.example.caravan.domain.model.OrderItem
import com.example.caravan.theme.Montserrat
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography

data class SavedCartOrder(
    val id: String?,
    val name: String,
    val price: Int,
    val amount: Int,
    val firstPicUrl: String
)

val items = listOf(
    SavedCartOrder(
        id = null,
        name = "myoeowae erw e",
        price = 2000,
        amount = 12,
        firstPicUrl = "https://images.frandroid.com/wp-content/uploads/2022/03/nothing-phone1-kv-1920x1080-1.jpg"
    ),
    SavedCartOrder(
        id = null,
        name = "myoeowae erw e",
        price = 2000,
        amount = 12,
        firstPicUrl = "https://images.frandroid.com/wp-content/uploads/2022/03/nothing-phone1-kv-1920x1080-1.jpg"
    ),
    SavedCartOrder(
        id = null,
        name = "myoeowae erw e",
        price = 2000,
        amount = 12,
        firstPicUrl = "https://images.frandroid.com/wp-content/uploads/2022/03/nothing-phone1-kv-1920x1080-1.jpg"
    )

)

@Composable
fun CartScreen(navController: NavHostController?, userId: String) {

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            elevation = 2.dp,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = PinkRed
        ) {
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "caravan",
                style = Typography.h1,
                textAlign = TextAlign.Start,
                color = Color.White
            )
        }
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {

            //orders
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(9f)
            ) {
                items(items = items) { item ->
                    CartOrderItem(item = item)
                }
            }


            //buy
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                MyButton(text = "Buy Now") {

                }
            }

        }
    }
}

@Composable
fun CartOrderItem(item: SavedCartOrder) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Card(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            AsyncImage(model = item.firstPicUrl, contentDescription = null, contentScale = ContentScale.Crop)
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .height(52.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.name,
                style = TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                maxLines = 1
            )

            Row(Modifier.fillMaxWidth()) {

                val pStyle = TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = PinkRed
                )

                val bStyle = TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = (item.price/100f).toString(),
                    style = bStyle,
                )
                Text(
                    text =" RS ",
                    style = pStyle,
                )

                Text(
                    text =  " x ",
                    style = bStyle,
                )

                Text(
                    text =  item.amount.toString(),
                    style = bStyle,
                )

                Text(
                    text =" Piece ",
                    style = pStyle,
                )


                Text(
                    text =" = ",
                    style = pStyle,
                )

                Text(
                    text =  (item.amount*(item.price/100f)).toString(),
                    style = bStyle,
                )


                Text(
                    text =" RS ",
                    style = pStyle,
                )
            }
        }

    }
}

@Preview()
@Composable
fun cartpre() {
    CartScreen(navController = null, userId = "")
}