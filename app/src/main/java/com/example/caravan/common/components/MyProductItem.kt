package com.example.caravan.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.caravan.domain.model.Product

@Composable
fun MyProductItem(item: Product, function: () -> Unit) {
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 2.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { function() }
    ) {
        Column() {
            AsyncImage(
                modifier = Modifier
                    .height(125.dp),
                model = item.imageUrls[0],
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(text = item.name)
            Text(text = item.newPrice.toString())
            Text(text = item.initPrice.toString())
            Text(text = item.minOrder)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }

}





