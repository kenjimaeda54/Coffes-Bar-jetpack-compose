package com.example.coffesbarcompose.route

enum class StackScreensApp {
    DetailsScreen,
    PaymentResume,
    PaymentFinished,
    Login,
    SignIn,
    MainScreen;


    companion object {
        fun fromRoute(route: String?): StackScreensApp = when(route?.substringBefore("/")) {
            DetailsScreen.name -> DetailsScreen
            PaymentResume.name-> PaymentResume
            PaymentFinished.name -> PaymentFinished
            Login.name -> Login
            SignIn.name -> SignIn
            MainScreen.name -> MainScreen
            else -> throw  IllegalArgumentException("Route $route is not recognizable")
        }
    }

}