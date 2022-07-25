package com.example.caravan.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.caravan.ui.theme.PinkRed

@Composable
fun Test(
    viewModel: MainViewModel = hiltViewModel()
) {
    viewModel.testCall()
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        Text(text = "Hello Test", color = PinkRed)
    }
}