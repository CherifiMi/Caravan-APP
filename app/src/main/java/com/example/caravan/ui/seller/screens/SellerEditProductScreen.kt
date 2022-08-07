package com.example.caravan.ui.seller.screens

import android.content.ContentResolver
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.common.components.MyButton
import com.example.caravan.common.components.MyTextField
import com.example.caravan.theme.Typography
import com.example.caravan.ui.seller.SellerViewModel
import com.example.caravan.ui.seller.components.CatsPicker
import com.example.caravan.ui.seller.components.PicsPicker


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SellerEditProductScreen(
    navController: NavHostController?,
    viewModel: SellerViewModel = hiltViewModel(),
    cn: ContentResolver,
    args: Bundle?
) {
    val itemId = args?.getString("item") ?: ""
    viewModel.setCurrentItemValues(itemId)

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "caravan",
                    style = Typography.h1,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            })
        }
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            PicsPicker(viewModel, cn)

            CatsPicker(viewModel)


            Text(
                modifier = Modifier.padding(16.dp),
                text = "Info",
                style = Typography.h1
            )

            MyTextField(state = viewModel.name, s = "Product name")
            MyTextField(state = viewModel.content, s = "Product Description")
            MyTextField(state = viewModel.minOrder, s = "Minimum Order Amount", isNum = true)
            MyTextField(state = viewModel.fPrice, s = "Original Price",  isNum = true)
            MyTextField(state = viewModel.sPrice, s = "Discounted Price",  isNum = true)

            Spacer(modifier = Modifier.padding(16.dp))

            MyButton(text = if (itemId.toInt()!=-1)"Update Product" else "Create a New Product") {

                if (itemId.toInt()==-1){
                    viewModel.createNewProduct(){
                        navController?.popBackStack()
                    }
                }else{
                    viewModel.updateProduct(){
                        navController?.popBackStack()
                    }
                }
            }

            Spacer(modifier = Modifier.padding(32.dp))
        }
    }

}


