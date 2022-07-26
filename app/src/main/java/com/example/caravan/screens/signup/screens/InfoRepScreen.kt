package com.example.caravan.screens.signup.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.caravan.components.MyButton
import com.example.caravan.components.MyTextField
import com.example.caravan.screens.signup.SignUpViewModel
import com.example.caravan.ui.theme.Typography

@Composable
fun InfoRepScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {

    Column(verticalArrangement = Arrangement.SpaceBetween) {

        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "caravan",
                style = Typography.h1,
                color = Color.White
            )
        })

        Column(Modifier.verticalScroll(rememberScrollState())) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Create Representative Account",
                style = Typography.h1
            )

            MyTextField(state = viewModel.gmail, s = "Email")
            MyTextField(state = viewModel.password, s = "Password")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Personal Information",
                style = Typography.h1
            )

            MyTextField(state = viewModel.first_name, s = "First Name")
            MyTextField(state = viewModel.last_name, s = "Last Name")
            MyTextField(state = viewModel.phone, s = "Phone Number")


            Spacer(modifier = Modifier.height(32.dp))

            MyButton(text = "Create my account") {

            }

            Spacer(modifier = Modifier.height(280.dp))
        }


    }
}