package com.example.caravan.ui.rep.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caravan.ui.rep.RepViewModel
import com.example.caravan.ui.rep.components.MyUserRepItem

@Composable
fun MySellersScreen(navController: NavHostController, viewModel: RepViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(viewModel.repMySellers.value){item ->
            MyUserRepItem(name = item!!.owner, brand = item.brand){

            }
        }
    }
}