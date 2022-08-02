package com.example.caravan.ui.seller.components

import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.caravan.common.components.MyButton
import com.example.caravan.common.components.MyTextField
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.theme.LightGrey
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography
import com.example.caravan.ui.seller.SellerViewModel



@Composable
fun SellerEditProduct(
    navController: NavHostController?,
    viewModel: SellerViewModel = hiltViewModel(),
    args: Bundle?
) {
    viewModel.setCurentItemValues(args?.getString("item")?:"")

    Column(verticalArrangement = Arrangement.SpaceBetween) {

        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "caravan",
                style = Typography.h1,
                color = Color.White
            )
        })


        Text(
            modifier = Modifier.padding(16.dp),
            text = "Images",
            style = Typography.h1
        )


        ImageGrid(viewModel)


        Text(
            modifier = Modifier.padding(16.dp),
            text = "Info",
            style = Typography.h1
        )

        MyTextField(state = viewModel.name, s = "Product name", isEM = true)
        MyTextField(state = viewModel.content, s = "Product Description", isEM = true)
        MyTextField(state = viewModel.minOrder, s = "Minimum Order Amount", isEM = true)
        MyTextField(state = viewModel.fPrice, s = "Original Price", isEM = true)
        MyTextField(state = viewModel.sPrice, s = "Discounted Price", isEM = true)
        
        Spacer(modifier = Modifier.padding(16.dp))
        
        MyButton(text = "Update Product") {
            
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageGrid(viewModel: SellerViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4) ,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(10.dp)
    ) {

        itemsIndexed(items = viewModel.images ) { index, item ->
            Card(
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(2.dp, PinkRed),
                modifier = Modifier
                    .padding(4.dp)
                    .height(80.dp)
            ){
                AsyncImage(model = item, contentDescription = null, contentScale = ContentScale.Crop)
            }
        }
    }
}
