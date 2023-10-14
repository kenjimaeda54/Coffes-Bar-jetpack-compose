package com.example.coffesbarcompose.screen.payment_finished

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.coffesbarcompose.route.BottomBarScreen
import com.example.coffesbarcompose.route.BottomScreens
import com.example.coffesbarcompose.route.StackScreens
import com.example.coffesbarcompose.ui.theme.fontsInter
import com.example.coffesbarcompose.view.ButtonCommon

@Composable
fun PaymentFinished(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp, start = 12.dp, top = 50.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Obrigado por comprar em nossas lojas, o " +
                        "pagamento será feito na entrega, vendedor precisa disponibilizar possibilidade pagamento por " +
                        "cartão de crédito, debito ou pix.\nFique a vontade para entrar em contato com nossos canais de comunicação para qualquer dúvida.",
                style = TextStyle(
                    fontFamily = fontsInter,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 25.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonCommon(
                modifier = Modifier.padding(bottom = 24.dp, top = 10.dp),
                title = "Voltar usar app",
                action = { navController.navigate(BottomBarScreen.Home.route) })
        }
    }
}

