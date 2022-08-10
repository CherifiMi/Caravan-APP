package com.example.caravan


import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.caravan.theme.CaravanTheme
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalMotionApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        configAWS()

        configStripe()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.spalsh.value
            }
        }

        setContent {
            CaravanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                //test()
                MainApp(viewModel, contentResolver)
                }
            }
        }
    }

    private fun configStripe() {
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51KkDEgCpXNrjS0vATGw0tV5pind5LMe49nAqDe41T3brgZc7J9bv7MQAopciLojJZFuKY4wUWUCAf2GVLoBXdLyw00LjLJRdni"
        )
    }


    fun test(){
        val weakActivity = WeakReference<Activity>(this)

        weakActivity.get()?.let {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(it, Uri.parse("https://www.youtube.com/watch?v=pzq9sQ8j-nk"))
        }
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

}

