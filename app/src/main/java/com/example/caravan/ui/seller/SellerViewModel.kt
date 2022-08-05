package com.example.caravan.ui.seller

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.caravan.R
import com.example.caravan.common.snackbar.SnackbarManager
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.Product
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.domain.use_cases.CreateNewProductUseCase
import com.example.caravan.domain.use_cases.GetAllSellerProductsUseCase
import com.example.caravan.domain.use_cases.UploadImageGetUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SellerViewModel @Inject constructor(
    private val getAllSellerProductsUseCase: GetAllSellerProductsUseCase,
    private val accountService: AccountService,
    private val createNewProductUseCase: CreateNewProductUseCase,
    private val uploadImageGetUrlUseCase: UploadImageGetUrlUseCase
) : ViewModel() {

    val tag = "SELLER_TEST"

    //__________________________values
    val loading = mutableStateOf(true)
    var myProducts: List<Product>? = null



    val name = mutableStateOf("")
    val content = mutableStateOf("")
    val cats = mutableListOf<String>()


    val image0 = mutableStateOf("")
    val image1 = mutableStateOf("")
    val image2 = mutableStateOf("")
    val image3 = mutableStateOf("")


    val fPrice = mutableStateOf("")
    val sPrice = mutableStateOf("")

    val minOrder = mutableStateOf("")

    val sellerId = mutableStateOf("")
    val sellerKey = mutableStateOf("")



    //___________________________functions
    fun getSellerProducts() {
        getAllSellerProductsUseCase(Id(accountService.getUserId())).onEach {
            when (it) {
                is Result.Success -> {
                    loading.value = false
                    myProducts = it.data
                    Log.d(tag, it.data.toString())
                }
                is Result.Error -> {
                    loading.value = true
                    Log.d(tag, it.message.toString())
                }

            }
        }.launchIn(viewModelScope)
    }

    fun signOut(navController: NavHostController) {

        accountService.signOut()

        navController.navigate(Screens.Main.route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }

    fun uploadImageGetUrl(x: MutableState<String>, uri: Uri, cn: ContentResolver) {
        uploadImageGetUrlUseCase(uri, cn, x)
    }

    fun CreateNewProduct() {
        if (false
        //all feilds are not full
        ) {

            SnackbarManager.showMessage(R.string.empty_data)
            return
        }

        viewModelScope.launch {
            //createNewProductUseCase(Product())
        }
    }

    fun setCurentItemValues(id: String) {

        getSellerProducts()
        val item = myProducts?.get(id.toInt())
        if (loading.value) {
            return
        }



        if (item != null) {
            name.value = item.name
            content.value = item.content

            sellerId.value = item.id
            sellerKey.value = item.sellerKey
            minOrder.value = item.minOrder

            item.let { if (cats.size < 4) cats.addAll(it.cat) }

            image0.value = item.imageUrls[0]
            image1.value = item.imageUrls[1]
            image2.value = item.imageUrls[2]
            image3.value = item.imageUrls[3]

            fPrice.value = item.initPrice.toString()
            sPrice.value = item.newPrice.toString()
        }
    }
}
