package com.example.caravan.ui.buyer.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.caravan.R
import com.example.caravan.common.components.MyButton
import com.example.caravan.domain.model.Product
import com.example.caravan.theme.*
import com.example.caravan.ui.buyer.BuyerViewModel


@Composable
fun BuyerProductScreen(
    navController: NavHostController,
    args: Bundle?,
    viewModel: BuyerViewModel = hiltViewModel()
) {

    val currantItem = viewModel.getCurrentProduct(args?.getString("index") ?: "")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BottomLayer(currantItem, viewModel, navController)
        TopLayer(currantItem, viewModel, navController)
    }


}

@Composable
fun TopLayer(currantItem: Product, viewModel: BuyerViewModel, navController: NavHostController) {


    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = viewModel.buyOrAddToCartSheet.value,
        enter = slideIn(
            initialOffset = { fullSize ->
                IntOffset(0, fullSize.width / 2)
            },
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)

        ),
        exit = slideOut(
            targetOffset = { fullSize ->
                IntOffset(0, fullSize.width / 2)
            },
            animationSpec = tween(durationMillis = 300)
        ) + fadeOut()
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .weight(6f)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { viewModel.buyOrAddToCartSheet.value = false }
            )

            Card(
                elevation = 10.dp,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                    
                    Row(Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.weight(1f)){
                            MyButton(
                                text = "Buy Now",
                                text_color = Color.White,
                                btn_color = PinkRed,
                                end = 8.dp
                            ) {

                            }
                        }

                        Box(modifier = Modifier.weight(1f)){

                            MyButton(
                                text = "Add to Cart",
                                has_border = true,
                                btn_color = Color.White,
                                text_color = PinkRed,
                                start = 8.dp
                            ) {

                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                }

            }
        }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomLayer(currantItem: Product, viewModel: BuyerViewModel, navController: NavHostController) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    viewModel.buyOrAddToCartSheet.value = true
                },
                backgroundColor = PinkRed
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.cart_full),
                    contentDescription = "Add To Cart",
                    tint = white
                )
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = currantItem.imageUrls[viewModel.thisImage.value],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ImagePickerCard(currantItem.imageUrls[0], 0, viewModel)
                    ImagePickerCard(currantItem.imageUrls[1], 1, viewModel)
                    ImagePickerCard(currantItem.imageUrls[2], 2, viewModel)
                    ImagePickerCard(currantItem.imageUrls[3], 3, viewModel)
                }
            }

            Card(
                shape = RoundedCornerShape(100.dp),
                backgroundColor = LightGrey,
                modifier = Modifier
                    .padding(150.dp, 16.dp)
                    .fillMaxWidth()
                    .height(4.dp)
            ) {}

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = currantItem.name + "Best Of Lalo Compilation | Better Call Saul\n",
                style = TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp
                )
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SR ",
                    color = PinkRed,
                    style = TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = currantItem.newPrice.toString() + ",00",
                    style = TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                )

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = "RS " + currantItem.initPrice.toString(),
                    textDecoration = TextDecoration.LineThrough,
                    style = Typography.h3,
                    color = LightGrey
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                modifier = Modifier.padding(16.dp),
                text = currantItem.content + stringResource(R.string.lorem_ipsem),
                style = TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            )

        }
    }
}

@Composable
fun ImagePickerCard(url: String, i: Int, viewModel: BuyerViewModel) {
    Spacer(modifier = Modifier.padding(2.dp))

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (viewModel.thisImage.value == i) PinkRed else LightGrey
        ),
        modifier = Modifier
            .size(width = 64.dp, height = 54.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                viewModel.thisImage.value = i
            }
    ) {

        AsyncImage(
            contentScale = ContentScale.Crop,
            model = url,
            contentDescription = null
        )

    }

    Spacer(modifier = Modifier.padding(2.dp))
}