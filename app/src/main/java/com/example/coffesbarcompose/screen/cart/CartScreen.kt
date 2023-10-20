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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffesbarcompose.mocks.ordersByUserMock
import com.example.coffesbarcompose.route.StackScreens
import com.example.coffesbarcompose.ui.theme.fontsInter
import com.example.coffesbarcompose.utility.Format
import com.example.coffesbarcompose.view.ButtonCommon
import com.example.coffesbarcompose.view.RowOrders
import com.example.coffesbarcompose.view.RowTitleAndSubTitle
import kotlin.random.Random


@Composable
fun CartScreen(navController: NavController) {
    val deliveryFee by rememberSaveable {
        mutableDoubleStateOf(Random.nextDouble(3.0, 6.0))
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
            ordersByUserMock.forEach {
                RowOrders(order = it)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            RowTitleAndSubTitle(
                tile = "Taxa de entrega",
                subTitle = Format.formatDoubleToMoneyReal(deliveryFee)
            )
            RowTitleAndSubTitle(tile = "Valor", subTitle = "R$ 35,00")
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            RowTitleAndSubTitle(tile = "Total", subTitle = "$ 35,00")
            ButtonCommon(
                modifier = Modifier.padding(bottom = 55.dp, top = 10.dp),
                title = "Finalizar a compra",
                action = { navController.navigate(StackScreens.PaymentResume.name) })
        }

    }
}