package com.example.caravan.screens.signup.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.caravan.R
import com.example.caravan.ui.theme.Typography

@Composable
fun WaitForAdminScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.wait_pic),
            contentDescription = null,
            modifier = Modifier.padding(88.dp)
        )

        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = "Our admins are chaking your requast to join",
            style = Typography.h6,
            textAlign = TextAlign.Center
        )
    }
}