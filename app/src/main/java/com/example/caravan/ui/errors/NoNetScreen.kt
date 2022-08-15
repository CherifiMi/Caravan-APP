package com.example.caravan.ui.errors

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.caravan.theme.Typography

@Composable
fun NoNetScreen(navController: NavHostController,) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "No Internet Connection.",
            style = Typography.h1,
            textAlign = TextAlign.Center
        )
    }
}