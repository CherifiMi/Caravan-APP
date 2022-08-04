package com.example.caravan


import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.compose.AsyncImage
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.caravan.theme.CaravanTheme
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.random
import java.time.LocalDateTime
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalMotionApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }

        var x = mutableStateOf("")

        fun getRandomKey(): String{
            val r = (LocalDateTime.now().toString()).replace(":", ""+(111111..999999).random()).replace(".", "")+".jpg"
            x.value = r
            return r
        }

        fun uploadPhoto(imageUri: Uri) {
            val stream = contentResolver.openInputStream(imageUri)!!

            Amplify.Storage.uploadInputStream(
                getRandomKey(),
                stream,
                { Log.d("Mito", "works") },
                { error -> Log.e("Mito", "Failed upload", error) }
            )
        }

        val getImageLauncher = registerForActivityResult(GetContent()) { uri ->
            uri?.let {
                uploadPhoto(it)
            }
        }





        //__________________________

        installSplashScreen().apply {
            //setKeepOnScreenCondition{
            //    viewModel.spalsh.value
            //}
        }


        setContent {
            CaravanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(Modifier.fillMaxSize()) {

                        Button(onClick = {
                            getImageLauncher.launch("image/*")
                            Log.d("MITO", x.value)
                        }) {
                            Text(text = "Open Gallery")
                        }
                        AsyncImage(
                            model = "https://caravan1e3ec66c1528495c9a1ce3f6b7172ef060631-dev.s3.eu-west-3.amazonaws.com/public/${x.value}",
                            contentDescription = null
                        )

                    }
                    //MainApp(viewModel)
                }
            }
        }
    }

}
