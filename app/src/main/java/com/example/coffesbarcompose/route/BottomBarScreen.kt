package com.example.coffesbarcompose.route
import com.example.coffesbarcompose.R

sealed class BottomBarScreen(
    val route: String,
    val icon: Int,
) {

    object Home : BottomBarScreen(
        route = "home",
        icon = R.drawable.home
    )

    object Favorite : BottomBarScreen(
        route = "favorite",
        icon = R.drawable.heart
    )

    object Cart : BottomBarScreen(
        route = "cart",
        icon = R.drawable.cart
    )

}