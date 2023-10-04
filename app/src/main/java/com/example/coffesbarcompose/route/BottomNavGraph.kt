package com.example.coffesbarcompose.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coffesbarcompose.screen.cart.CartScreen
import com.example.coffesbarcompose.screen.favorite.FavoriteScreen
import com.example.coffesbarcompose.screen.home.HomeScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen()
        }

        composable(BottomBarScreen.Cart.route) {
            CartScreen()
        }

        composable(BottomBarScreen.Favorite.route) {
            FavoriteScreen()
        }

    }
}


