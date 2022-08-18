package com.example.caravan.ui.rep.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography

@Composable
fun MyUserRepItem(name: String, brand: String, function: () -> Unit) {

    val nameList = name.split(" ")
    val firstChars = "${nameList[0].first()}${nameList[1].first()}".capitalize()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { function() }
    ) {
        Card(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = PinkRed
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = firstChars,
                    style = Typography.h1,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Column(Modifier.fillMaxWidth()) {

        }
    }
}

@Preview
@Composable
fun pre() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MyUserRepItem(name = "mito w", brand = "the dd") {

        }
    }
}