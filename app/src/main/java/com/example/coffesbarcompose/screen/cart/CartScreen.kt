package com.example.coffesbarcompose.screen.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.coffesbarcompose.route.StackScreensApp
import com.example.coffesbarcompose.ui.theme.fontsInter
import com.example.coffesbarcompose.utility.Format
import com.example.coffesbarcompose.view.ButtonCommon
import com.example.coffesbarcompose.view.ComposableLifecycle
import com.example.coffesbarcompose.view.RowOrders
import com.example.coffesbarcompose.view.RowTitleAndSubTitle
import com.example.coffesbarcompose.view_models.CartViewModel
import kotlin.random.Random


@Composable
fun CartScreen(navController: NavController, parentViewModel: CartViewModel) {
    val ordersByUser by remember(parentViewModel.returnListOrders()) {
        mutableStateOf(parentViewModel.returnListOrders()[0])
    }
    val totalPrice by remember(ordersByUser) {
        val price = ordersByUser.priceCartTotal.split("R$")[1].replace(",", ".")
            .toDouble() + ordersByUser.tax.split("R$")[1].replace(",", ".").toDouble()
        mutableStateOf(
            Format.formatDoubleToMoneyReal(price)
        )
    }



    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.8f)
                .padding(all = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cart", modifier = Modifier.padding(bottom = 20.dp), style = TextStyle(
                    fontFamily = fontsInter,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 18.sp
                )
            )
            ordersByUser.orders.forEach {
                RowOrders(order = it)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            RowTitleAndSubTitle(
                tile = "Taxa de entrega",
                subTitle = ordersByUser.tax
            )
            RowTitleAndSubTitle(tile = "Valor", subTitle = ordersByUser.priceCartTotal)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            RowTitleAndSubTitle(tile = "Total", subTitle = totalPrice)
            ButtonCommon(
                modifier = Modifier.padding(bottom = 55.dp, top = 10.dp),
                title = "Finalizar a compra",
                action = { navController.navigate(StackScreensApp.PaymentResume.name) })
        }

    }
}