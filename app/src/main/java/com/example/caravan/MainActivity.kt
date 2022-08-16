package com.example.caravan


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.caravan.common.snackbar.SnackbarManager
import com.example.caravan.domain.ConnectivityObserver
import com.example.caravan.domain.NetworkConnectivityObserver
import com.example.caravan.domain.model.mokeCats
import com.example.caravan.theme.CaravanTheme
import com.example.caravan.ui.buyer.BuyerViewModel
import com.google.gson.Gson
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.Address
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import dagger.hilt.android.AndroidEntryPoint

val canOrder = mutableStateOf(false)

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var buyerViewModel: BuyerViewModel
    lateinit var connectivityObserver: ConnectivityObserver

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalMotionApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        configAWS()

        configStripe()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.spalsh.value
            }
        }

        val paymentConfiguration = PaymentConfiguration.getInstance(applicationContext)

        val paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )
        setContent {

            val netStatus by connectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.Unavailable)
            viewModel.there_is_net.value = netStatus == ConnectivityObserver.Status.Available

            Log.d("MitoNet", netStatus.name)
            buyerViewModel = hiltViewModel()
            CaravanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Log.d("Mito", Gson().toJson(mokeCats))
                    MainApp(viewModel, contentResolver, paymentLauncher)
                }
            }
        }
    }

    private fun configStripe() {
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51LWUO5SACQklfQRYruy9W29tWhUQ1gjBpke7xQEH6uoZ7Q1CcGlSzQyPBl6u9aMY4yWqlQJLXgMCbVCgszADY9VV00o6ORtDAr"
        )
    }

    private fun configAWS() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }

    private fun onPaymentResult(paymentResult: PaymentResult) {

        when (paymentResult) {
            is PaymentResult.Completed -> {
                buyerViewModel.makeOrder()
            }
            is PaymentResult.Canceled -> {
                SnackbarManager.showMessage(R.string.no_pay)
            }
            is PaymentResult.Failed -> {
                "Failed: " + paymentResult.throwable.message
                SnackbarManager.showMessage(R.string.no_pay)
            }
        }
    }
}

