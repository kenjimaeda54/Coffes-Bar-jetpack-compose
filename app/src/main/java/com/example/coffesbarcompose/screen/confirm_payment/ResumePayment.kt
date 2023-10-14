package com.example.coffesbarcompose.screen.confirm_payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Surface
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.route.StackScreens
import com.example.coffesbarcompose.utils.Format
import com.example.coffesbarcompose.view.ButtonCommon
import com.example.coffesbarcompose.view.ButtonCustomOutline
import com.example.coffesbarcompose.view.RowTitleAndSubTitle
import kotlin.random.Random

@Composable
fun PaymentResume() {
    val deliveryFee by remember {
        mutableDoubleStateOf(Random.nextDouble(3.0, 6.0))
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp, start = 12.dp, top = 50.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonCustomOutline(
                    modifier = Modifier
                        .fillMaxWidth(0.5f), action = { /*TODO*/ }, text = "Rua"
                )
                ButtonCustomOutline(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    action = { /*TODO*/ },
                    text = "Numero"
                )
            }
            ButtonCustomOutline(action = { /*TODO*/ }, text = "Bairro")
            ButtonCustomOutline(modifier = Modifier.padding(bottom = 10.dp), action = { /*TODO*/ }, text = "Cidade")

            Surface(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.tertiary
            ) {
                Image(
                    modifier = Modifier.padding(all = 2.dp),
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = "Icon Location",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            RowTitleAndSubTitle(
                tile = "Taxa de entrega",
                subTitle = "R$ ${Format.formatDoubleToMoneyReal(deliveryFee)}"
            )
            RowTitleAndSubTitle(tile = "Valor", subTitle = "R$ 35,00")
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            RowTitleAndSubTitle(tile = "Total", subTitle = "$ 35,00")
            ButtonCommon(
                modifier = Modifier.padding(bottom = 24.dp, top = 10.dp),
                title = "Tudo certo",
                action = { })

        }
    }


}


@Composable
@Preview
fun ConfirmPaymentScreenPreview() {

}