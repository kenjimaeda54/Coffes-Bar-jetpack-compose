package com.example.coffesbarcompose.route

import java.lang.IllegalArgumentException

enum class StackScreens {
    MainScreen,
    DetailsScreen,
    PaymentResume,
    PaymentFinished;

    companion object {
        fun fromRoute(route: String?): StackScreens = when(route?.substringBefore("/")) {
            DetailsScreen.name -> DetailsScreen
            PaymentResume.name-> PaymentResume
            PaymentFinished.name -> PaymentFinished
            MainScreen.name -> MainScreen
            else -> throw  IllegalArgumentException("Route $route is not recognizable")
        }
    }

}