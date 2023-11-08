package com.example.coffesbarcompose.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.coffesbarcompose.screen.MainScreen
import com.example.coffesbarcompose.screen.cart.CartScreen
import com.example.coffesbarcompose.screen.details.DetailsScreen
import com.example.coffesbarcompose.screen.favorite.FavoriteScreen
import com.example.coffesbarcompose.screen.home.HomeScreen
import com.example.coffesbarcompose.screen.login.LogInScreen
import com.example.coffesbarcompose.screen.payment_finished.PaymentFinished
import com.example.coffesbarcompose.screen.payment_resume.PaymentResume
import com.example.coffesbarcompose.screen.sign_in.SignIn
import com.example.coffesbarcompose.view_models.CartViewModel
import com.example.coffesbarcompose.view_models.CoffeesViewModel


@Composable
fun NavGraphApp(navController: NavHostController, isAnonymous: MutableState<Boolean>) {

    NavHost(
        navController = navController,
        startDestination = if (isAnonymous.value) StackScreensApp.Login.name else BottomBarScreen.Home.route
    ) {

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
        ) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentCartViewModel = hiltViewModel<CartViewModel>(parentEntry)
            val parentCoffeesViewModel = hiltViewModel<CoffeesViewModel>(parentEntry)
            DetailsScreen(
                coffeeId = entry.arguments?.getString("coffeeId"),
                cartViewModel = parentCartViewModel,
                coffeesViewModel = parentCoffeesViewModel
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


        composable(StackScreensApp.Login.name) {
            LogInScreen(navController = navController)
        }
        composable(StackScreensApp.SignIn.name) {
            SignIn(navController = navController)
        }
        composable(StackScreensApp.MainScreen.name) {
            MainScreen()
        }
    }
}

