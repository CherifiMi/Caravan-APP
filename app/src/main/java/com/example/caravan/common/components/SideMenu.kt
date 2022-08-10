package com.example.caravan.common.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.MainViewModel

@Composable
fun SideMenu(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
){
    
    Button(onClick = { viewModel.signOut(navController) }) {
        Text(text = "sign out")
    }
    
}