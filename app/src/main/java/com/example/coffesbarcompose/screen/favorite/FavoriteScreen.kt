package com.example.coffesbarcompose.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coffesbarcompose.mocks.favoritesMock
import com.example.coffesbarcompose.view.RowOrders


@Composable
fun FavoriteScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        LazyColumn(modifier = Modifier.padding(all = 20.dp)) {
            items(favoritesMock) {
                Column(modifier = Modifier.padding(vertical = 20.dp)) {
                    Text("Valor total: ${it.priceCartTotal}")
                    it.orders.forEach { orders ->
//                        RowOrders(modifier = Modifier.padding(vertical = 10.dp), coffee = orders)
                    }
                }

            }
        }
    }
}