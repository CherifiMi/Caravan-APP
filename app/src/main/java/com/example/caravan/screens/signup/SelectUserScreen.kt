package com.example.caravan.screens.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.caravan.R
import com.example.caravan.components.MyButton
import com.example.caravan.screens.signup.components.UserCard
import com.example.caravan.ui.theme.LightGrey
import com.example.caravan.ui.theme.PinkRed
import com.example.caravan.ui.theme.Typography

@Composable
fun SelectUserScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopAppBar(title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "caravan",
                    style = Typography.h1,
                    color = Color.White
                )
            })


            Text(
                modifier = Modifier.padding(vertical = 32.dp),
                text = "Join as a buyer, a seller\n" +
                        "or a representative",
                style = Typography.h6,
                textAlign = TextAlign.Center
            )

            UserCard(
                painterResource(id = R.drawable.buyer_pic),
                "I am a buyer, looking for" +
                        "\n sellers",
                1,
                viewModel.selected_type
            )
            UserCard(
                painterResource(id = R.drawable.seller_pic),
                "I am a sellers, looking for " +
                        "\n buyer",
                2,
                viewModel.selected_type
            )
            UserCard(
                painterResource(id = R.drawable.rep_pic),
                "I am a representative, looking" +
                        "\n for stuff",
                3,
                viewModel.selected_type
            )
        }
        //____________________
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyButton(
                text = "Apply as a ${
                    when (viewModel.selected_type.value) 
                    {
                        1 -> "Buyer"
                        2 -> "Seller"
                        else -> "Representative"
                    }
                }"
            ) {

            }
            Row(Modifier.padding(top = 8.dp)) {
                Text(
                    text = "Do you have an accont?",
                    color = LightGrey,
                    style = Typography.h3
                )
                Text(
                    modifier = Modifier
                        .clickable {

                        },
                    text = "Login",
                    color = PinkRed,
                    style = Typography.h3
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}