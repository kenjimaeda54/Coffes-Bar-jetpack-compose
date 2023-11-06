package com.example.coffesbarcompose.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.coffesbarcompose.screen.MainScreen
import com.example.coffesbarcompose.screen.cart.CartScreen
import com.example.coffesbarcompose.screen.payment_resume.PaymentResume
import com.example.coffesbarcompose.screen.details.DetailsScreen
import com.example.coffesbarcompose.screen.favorite.FavoriteScreen
import com.example.coffesbarcompose.screen.home.HomeScreen
import com.example.coffesbarcompose.screen.payment_finished.PaymentFinished
import com.example.coffesbarcompose.view_models.CartViewModel


@Composable
fun NavGraphApp(navController: NavHostController) {

    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route) {
        composable(StackScreensInitial.MainScreen.name) {
            MainScreen()
        }


        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }


        composable(BottomBarScreen.Favorite.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
                //tem que ser uma rota ja existente se colocar rota do  carrinho nao vai funcionar, porque o usuario nao inicia nessas tela, ele iria precisar navegar para eu ter a instancia e  favoritos posso ir direto da home
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            FavoriteScreen(cartViewModel = parentViewModel, navController)
        }

        //maneira de compartilhar view model via navegacao
        //https://developer.android.com/jetpack/compose/libraries?hl=pt-br#hilt-navigation
        composable(BottomBarScreen.Cart.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            CartScreen(navController, parentViewModel)
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
        composable(route = StackScreensApp.PaymentResume.name) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            PaymentResume(navController, parentViewModel = parentViewModel)
        }

        composable(route = StackScreensApp.PaymentFinished.name) {

            PaymentFinished(navController)
        }
    }
}

