package com.example.caravan.ui.seller.components

import android.content.ContentResolver
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.caravan.common.components.MyButton
import com.example.caravan.common.components.MyTextField
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography
import com.example.caravan.ui.seller.SellerViewModel



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SellerEditProduct(
    navController: NavHostController?,
    viewModel: SellerViewModel = hiltViewModel(),
    cn: ContentResolver,
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


        ImageGrid(viewModel, cn)


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



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageGrid(viewModel: SellerViewModel, cn: ContentResolver) {




    LazyVerticalGrid(
        cells = GridCells.Fixed(4) ,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(10.dp)
    ) {

        item {
            ImageCard(x = viewModel.image0, viewModel = viewModel, cn)
        }
        item {
            ImageCard(x = viewModel.image1, viewModel = viewModel, cn = cn)
        }
        item {
            ImageCard(x = viewModel.image2, viewModel = viewModel, cn = cn)
        }
        item {
            ImageCard(x = viewModel.image3, viewModel = viewModel, cn = cn)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ImageCard(x: MutableState<String>, viewModel: SellerViewModel, cn: ContentResolver) {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.uploadImageGetUrl(x, uri, cn = cn)
        }
    }

    Card(
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(2.dp, PinkRed),
        modifier = Modifier
            .padding(4.dp)
            .height(80.dp)
            .clickable {
                launcher.launch("image/*")

            }
    ){
        AsyncImage(model = x.value, contentDescription = null, contentScale = ContentScale.Crop)
    }
}