package com.example.coffesbarcompose.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.mocks.coffeesMock
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.ui.theme.fontsInter
import com.example.coffesbarcompose.view.ButtonCommon

@Composable
fun DetailsScreen(navController: NavController, coffeeId: String?) {
    val coffee by remember(coffeeId) {
        mutableStateOf<CoffeesModel?>(
            coffeesMock.first { it._id == coffeeId }
        )
    }
    val heightImage = LocalConfiguration.current.screenHeightDp / 2

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp, end = 5.dp, start = 5.dp, bottom = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(7.dp))
                    .fillMaxWidth()
                    .height(heightImage.dp),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(coffee!!.urlPhoto)
                    .build(),
                contentDescription = "Image Coffee",
                contentScale = ContentScale.FillHeight
            )
            Text(
                coffee!!.name, modifier = Modifier.padding(vertical = 10.dp), style = TextStyle(
                    fontFamily = fontsInter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary,
                )
            )
            Text(
                text = coffee!!.description,
                modifier = Modifier.padding(bottom = 10.dp),
                style = TextStyle(
                    fontFamily = fontsInter,
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    lineHeight = 25.sp,
                    color = MaterialTheme.colorScheme.secondary,
                )
            )
            //usando Spacer com weight ira forcar tudo que esta abaixo dividir igualmente o espaco entre
            // o row e o restante da tela, empurrando assim para o final
            //caso tenha espaco
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.padding(end = 15.dp)) {
                    Text(
                        "Preço", style = TextStyle(
                            fontFamily = fontsInter,
                            fontWeight = FontWeight.Light,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    Text(
                        text = coffee!!.price, style = TextStyle(
                            fontFamily = fontsInter,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    )
                }
                ButtonCommon(title = "Comprar", action = {})
            }

        }
    }

}


