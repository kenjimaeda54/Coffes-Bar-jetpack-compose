package com.example.coffesbarcompose.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.coffesbarcompose.mocks.favoritesMock
import com.example.coffesbarcompose.models.OrdersByUserModel
import com.example.coffesbarcompose.route.BottomBarScreen
import com.example.coffesbarcompose.view.ComposableLifecycle
import com.example.coffesbarcompose.view.RowCoffeePlaceHolder
import com.example.coffesbarcompose.view.RowOrders
import com.example.coffesbarcompose.view.TitlePlaceHolder
import com.example.coffesbarcompose.view_models.CartViewModel
import com.example.coffesbarcompose.view_models.CoffeesViewModel


@Composable
fun FavoriteScreen(
    cartViewModel: CartViewModel,
    navController: NavController,
    coffeesViewModel: CoffeesViewModel = hiltViewModel()
) {

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            cartViewModel.getOrder()
            coffeesViewModel.getAllCoffees()
        }
    }

    //verficiar como navegar e verificar se popssuir maior quantidade
    //verfiuicar se tem os botoes

    fun handleNavigation(ordersByUser: OrdersByUserModel) {
        cartViewModel.addToCartByFavorites(ordersByUser)
        navController.navigate(BottomBarScreen.Cart.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }


    }


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        if (cartViewModel.allOrders.value.isLoading!! || cartViewModel.allOrders.value.exception != null) {
            LazyColumn(modifier = Modifier.padding(all = 20.dp)) {
                items(favoritesMock) {
                    Column(modifier = Modifier.padding(vertical = 20.dp)) {
                        TitlePlaceHolder()
                        it.orders.forEach { _ ->
                            RowCoffeePlaceHolder()
                        }
                    }

                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(all = 20.dp)) {
                items(cartViewModel.allOrders.value.data!!.reversed()) {
                    Column(modifier = Modifier.padding(vertical = 20.dp)) {
                        Text("Valor total: ${it.priceCartTotal}")
                        it.orders.forEach { order ->
                            RowOrders(
                                modifier = Modifier.padding(vertical = 10.dp),
                                order = order,
                                enableSwipe = false,
                                actionClickable = { handleNavigation(it) })
                        }
                    }

                }
            }
        }

    }
}