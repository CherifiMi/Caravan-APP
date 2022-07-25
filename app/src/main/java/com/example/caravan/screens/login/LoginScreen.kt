package com.example.caravan.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.caravan.R
import com.example.caravan.components.MyButton
import com.example.caravan.ui.theme.GoogleBlue
import com.example.caravan.ui.theme.PinkRed
import com.example.caravan.ui.theme.Typography


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
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
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            })


            Text(
                modifier = Modifier.padding(vertical = 40.dp),
                style = Typography.h2,
                text = "Login to Caravan",
                color = Color.Black
            )


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp, top = 0.dp),
                value = viewModel.email.value,
                label = { Text("Email") },
                singleLine = true,
                onValueChange = {
                    viewModel.email.value = it
                }
            )


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp, top = 0.dp),
                value = viewModel.password.value,
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = {
                    viewModel.password.value = it
                }
            )

            MyButton(
                text = "Continue with Email",
                text_color = Color.White,
                btn_color = PinkRed,
                fanction = { viewModel.Login() }
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.or),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            MyButton(
                text = "Continue with Google",
                text_color = Color.White,
                btn_color = GoogleBlue,
                fanction = { viewModel.Login() }
            )

        }
        //____
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.dont),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            MyButton(
                text = "Sign Up",
                text_color = PinkRed,
                btn_color = Color.White,
                has_border = true,
                fanction = { viewModel.Login() }
            )

            Spacer(modifier = Modifier.height(72.dp))
        }

    }
}