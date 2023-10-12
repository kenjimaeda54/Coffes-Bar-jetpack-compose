package com.example.coffesbarcompose.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.mocks.coffeesMock
import com.example.coffesbarcompose.route.StackScreens
import com.example.coffesbarcompose.ui.theme.fontsPacifico
import com.example.coffesbarcompose.view.RowCoffee

@Composable
fun HomeScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(modifier = Modifier.padding(all = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                            fontFamily = fontsPacifico,
                            fontWeight = FontWeight.Normal,
                            fontSize = 30.sp
                        )
                    ) {
                        append("Coffees \n \n")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontFamily = fontsPacifico,
                            fontWeight = FontWeight.Normal,
                            fontSize = 40.sp
                        )
                    ) {
                        append("Bar \n")
                    }
                })
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(60.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://firebasestorage.googleapis.com/v0/b/uploadimagesapicoffee.appspot.com/o/avatar01.png?alt=media&token=4a3820fa-b757-4bcd-b148-1cd914956112")
                        .build(), contentDescription = " Image avatar user"
                )
            }
            LazyVerticalGrid(columns = GridCells.FixedSize(145.dp), horizontalArrangement = Arrangement.SpaceBetween, contentPadding = PaddingValues(bottom = 100.dp)) {
                items(coffeesMock) {
                     RowCoffee(modifier = Modifier.clickable { navController.navigate(StackScreens.DetailsScreen.name + "/${it.id}") },coffee = it)

                }
            }


        }
    }
}