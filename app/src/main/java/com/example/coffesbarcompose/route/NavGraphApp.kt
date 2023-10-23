package com.example.coffesbarcompose.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.coffesbarcompose.screen.cart.CartScreen
import com.example.coffesbarcompose.screen.payment_resume.PaymentResume
import com.example.coffesbarcompose.screen.details.DetailsScreen
import com.example.coffesbarcompose.screen.favorite.FavoriteScreen
import com.example.coffesbarcompose.screen.home.HomeScreen
import com.example.coffesbarcompose.screen.payment_finished.PaymentFinished

@Composable
fun NavGraphApp(navController: NavHostController) {

    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }

        composable(BottomBarScreen.Favorite.route) {
            FavoriteScreen()
        }

        composable(BottomBarScreen.Cart.route) {
            CartScreen(navController)
        }

        composable(
            StackScreensApp.DetailsScreen.name + "/{coffeeId}",
            arguments = listOf(navArgument("coffeeId") { type = NavType.StringType })
        ) {
            DetailsScreen(
                navController = navController,
                coffeeId = it.arguments?.getString("coffeeId")
            )
        }
        composable(route = StackScreensApp.PaymentResume.name) {
            PaymentResume(navController)
        }

        composable(route = StackScreensApp.PaymentFinished.name) {
            PaymentFinished(navController)
        }
    }
}


