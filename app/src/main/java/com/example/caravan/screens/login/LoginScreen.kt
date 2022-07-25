package com.example.caravan.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.caravan.screens.MainViewModel
import com.example.caravan.ui.theme.PinkRed


@Composable
fun LoginScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "caravan",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        })

        Text(
            modifier = Modifier.padding(vertical = 40.dp),
            style = MaterialTheme.typography.h6,
            text = "Login to Caravan",
            color = Color.Black
        )
    }
}