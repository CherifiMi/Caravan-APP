package com.example.caravan.ui.seller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.theme.Typography
import com.example.caravan.ui.signup.SignUpViewModel

@Composable
fun SellerHomeScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Seller with id: ${viewModel.getUserId()}", style = Typography.h1, textAlign = TextAlign.Center)
    }
}