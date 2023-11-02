package com.example.coffesbarcompose.screen.cart

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.coffesbarcompose.modifier.detectSwipe
import com.example.coffesbarcompose.route.StackScreensApp
import com.example.coffesbarcompose.ui.theme.fontsInter
import com.example.coffesbarcompose.utility.Format
import com.example.coffesbarcompose.view.ButtonCommon
import com.example.coffesbarcompose.view.ComposableLifecycle
import com.example.coffesbarcompose.view.RowOrders
import com.example.coffesbarcompose.view.RowTitleAndSubTitle
import com.example.coffesbarcompose.view_models.CartViewModel
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(navController: NavController, parentViewModel: CartViewModel) {
    val isLoading by remember {
        mutableStateOf(false)
    }
    val totalPrice by remember(parentViewModel.orderCart.value.priceCartTotal) {
        if (parentViewModel.orderCart.value.priceCartTotal.count() > 2) {
            val price =
                parentViewModel.orderCart.value.priceCartTotal.split("R$")[1].replace(",", ".")
                    .toDouble() + parentViewModel.orderCart.value.tax.split("R$")[1].replace(
                    ",",
                    "."
                )
                    .toDouble()
            mutableStateOf(
                Format.formatDoubleToMoneyReal(price)
            )
        } else {
            mutableStateOf("")
        }
    }

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            if (parentViewModel.coffeesAdded.isNotEmpty()) {
                parentViewModel.handleOrderToCart()
            }
        }

    }

    fun handleNavigation() {
        parentViewModel.createCart(parentViewModel.orderCart.value)
        navController.navigate(StackScreensApp.PaymentResume.name)

    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.8f)
                .padding(all = 20.dp)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (parentViewModel.coffeesAdded.isNotEmpty()) {
                Text(
                    text = "Cart", modifier = Modifier.padding(bottom = 20.dp), style = TextStyle(
                        fontFamily = fontsInter,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 18.sp
                    )
                )
                parentViewModel.orderCart.value.orders.forEach {

                    RowOrders(
                        order = it,
                        actionAdd = { parentViewModel.handleAddQuantityToCart(it) },
                        actionRemove = { parentViewModel.handleRemoveQuantityToCart(it) },
                        actionDeleteOrder = { parentViewModel.handleRemoveToCart(it) }
                    )

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
                Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                RowTitleAndSubTitle(
                    tile = "Taxa de entrega",
                    subTitle = parentViewModel.orderCart.value.tax
                )
                RowTitleAndSubTitle(
                    tile = "Valor",
                    subTitle = parentViewModel.orderCart.value.priceCartTotal
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                RowTitleAndSubTitle(tile = "Total", subTitle = totalPrice)
                ButtonCommon(
                    modifier = Modifier.padding(bottom = 55.dp, top = 10.dp),
                    title = "Finalizar a compra",
                    action = { handleNavigation() },
                    feedbackLoading = isLoading

                )
            } else {
                Text(
                    text = "Você não possui nada no carrinho, vamos à compra", style = TextStyle(
                        fontFamily = fontsInter,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

    }
}