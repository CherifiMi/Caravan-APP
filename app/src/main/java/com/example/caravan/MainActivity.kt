package com.example.caravan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.caravan.screens.MainViewModel
import com.example.caravan.screens.login.LoginScreen
import com.example.caravan.screens.signup.SelectUserScreen
import com.example.caravan.screens.signup.screens.InfoBuyerScreen
import com.example.caravan.screens.signup.screens.InfoRepScreen
import com.example.caravan.screens.signup.screens.InfoSellerScreen
import com.example.caravan.screens.signup.screens.WaitForAdminScreen
import com.example.caravan.ui.theme.CaravanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            //setKeepOnScreenCondition{viewModel.spalsh.value}
        }

        setContent {
            CaravanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    WaitForAdminScreen()
                }
            }
        }
    }
}

