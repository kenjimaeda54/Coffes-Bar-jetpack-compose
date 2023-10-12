package com.example.coffesbarcompose.screen.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.mocks.coffeesMock
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.view.ButtonCommon

@Composable
fun DetailsScreen(navController: NavController, coffeeId: String?) {
    val coffee by remember(coffeeId) {
        mutableStateOf<CoffeesModel?>(
            coffeesMock.first { it.id == coffeeId }
        )
    }
    val heightImage = LocalConfiguration.current.screenHeightDp / 2

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(modifier = Modifier
            .padding(all = 5.dp)
            .verticalScroll(rememberScrollState())) {
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
                Text(coffee!!.name)
                Text(text = coffee!!.description)
                Row {
                    Column {
                        Text("Preço")
                        Text(text = coffee!!.price)
                    }
                    ButtonCommon()
                }

            }
    }

}


