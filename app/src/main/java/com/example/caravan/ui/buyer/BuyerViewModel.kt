package com.example.caravan.ui.buyer


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caravan.MainViewModel
import com.example.caravan.R
import com.example.caravan.common.snackbar.SnackbarManager
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.*
import com.example.caravan.domain.use_cases.GetProductsUseCase
import com.example.caravan.domain.use_cases.MakeOrderUseCase
import com.example.makeitso.common.snackbar.SnackbarMessage
import com.google.gson.Gson
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val repository: CaravanRepository,
    private val makeOrderUseCase: MakeOrderUseCase,
) : ViewModel() {

    //_____________________________value
    val thisImage = mutableStateOf(0)
    val buyOrAddToCartSheet = mutableStateOf(false)
    val loading = mutableStateOf(false)
    val catsPopUp = mutableStateOf(false)

    val amountToBuy = mutableStateOf("")

    val savedData =
        try {
            Gson().fromJson(
                runBlocking(Dispatchers.IO) { repository.getSavedProductList() }.productList,
                ProductsList::class.java
            )
        } catch (e: Exception) {
            listOf<Product>()
        }

    val x: MutableState<List<Product>> = mutableStateOf(savedData ?: emptyList())
    var selectedCat = mutableStateOf(-1)

    var buyerId = ""//mainViewModel.userId //accountService.getUserId()

    val myCats = runBlocking {
        repository.getSavedCats()
    }
    //_____________________________functions

    fun getAllSavedCardOrders(): List<SavedCartOrder> {

        return runBlocking<List<SavedCartOrder>> {
            try {
                repository.getAllCartOrder()
            } catch (e: Exception) {
                emptyList()
            }
        }

    }

    fun saveOrderToCart(currantItem: Product, amount: Int) {

        val savedCartOrder = SavedCartOrder(
            name = currantItem.name,
            price = currantItem.newPrice,
            amount = amount,
            sellerId = currantItem.sellerKey,
            sellerStripe = currantItem.sellerStripe,
            buyerId = buyerId,
            firstPicUrl = currantItem.imageUrls[0],
            productId = currantItem.id!!
        )

        viewModelScope.launch {
            repository.addCartOrder(savedCartOrder)
        }
    }

    fun saveOrder(product: Product, amount: Int) {
        viewModelScope.launch {
            repository.saveItem(product, amount)
        }
    }

    fun buyProduct(linked: String, paymentLauncher: PaymentLauncher, price: Int, amount: Int) {

        val paymentIntent = runBlocking {
            repository.paymentIntent(
                linked = linked,
                amount = price * amount,
                cur = "inr"
            )
        }.replace("\"", "")

        Log.d("Mito", "yoo: $paymentIntent")

        val card =
            PaymentMethodCreateParams.create(
                PaymentMethodCreateParams.Card.Builder()
                    .setNumber("4242424242424242")
                    .setExpiryMonth(1)
                    .setExpiryYear(2025)
                    .setCvc("123")
                    .build()
            )

        card.let { params ->
            val confirmPaymentIntentParams =
                ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                    paymentMethodCreateParams = params,
                    clientSecret = paymentIntent
                )
            paymentLauncher.confirm(confirmPaymentIntentParams)
        }
    }

    fun makeOrder() {
        Log.d("ORDERTEST", "order")
        viewModelScope.launch {

            val currantItem = repository.getSavedItem().product
            val currantAmount = repository.getSavedItem().amount

            makeOrderUseCase(
                Order(
                    id = null,
                    seller = currantItem.sellerKey,
                    buyer = buyerId,
                    amount = currantAmount,
                    productId = currantItem.name
                )
            )
        }
    }

    fun getCurrentProduct(s: String): Product {
        val currantItem = savedData.filter {
            it.id == s
        }[0]

        return currantItem
    }

    fun resetUserList() {
        selectedCat.value = -1
        x.value = savedData
    }


    fun getProducts() {
        Log.d("TESTAPI", "loading")

        getProductsUseCase().onEach {
            when (it) {
                is Result.Loading -> {
                    Log.d("TESTAPI", "loading")
                }
                is Result.Success -> {
                    repository.saveProductList(it.data)
                    x.value = it.data!!
                    loading.value = true
                    Log.d("TESTAPI", it.data.toString())
                }
                is Result.Error -> {
                    loading.value = true
                    Log.d("TESTAPI", it.message.toString())
                }

            }
        }.launchIn(viewModelScope)
    }

    fun changeMyList() {
        x.value =
            savedData.filter { product: Product ->
                var b = false
                for (i in myCats[selectedCat.value].subCats) {
                    if (product.cat.contains(i)) {
                        b = true
                        break
                    }
                }
                b
            }

    }

    fun selectedSubCat(item: String) {

        x.value =
            savedData.filter { product: Product ->
                var b = false
                for (i in product.cat) {
                    if (item == i) {
                        b = true
                        break
                    }
                }
                b
            }

        catsPopUp.value = false
    }

    fun search(s: String) {
        x.value =
            savedData.filter { product: Product ->
                product.name.contains(s)
            }

    }

    fun isAmountValid(s: String, min: Int, inv: Int): Boolean {

        //is empty
        if(s.isNullOrEmpty()){
            SnackbarManager.showMessage(R.string.invalid_amount)
            return false
        }

        //is not only nums
        s.toList().forEach { s ->
            try {
                s.toInt()
            }catch (e:Exception){
                SnackbarManager.showMessage(R.string.invalid_amount)
                return false
            }
        }

        //lower then min amount
        if (s.toInt()<min) {
            SnackbarManager.showMessage(R.string.less_then_min)
            return false
        }

        //have in inv
        if (s.toInt()>inv){
            SnackbarManager.showMessage(R.string.low_inv)
            return false
        }

        SnackbarManager.showMessage(com.stripe.android.R.string.done)
        return true
    }

}


