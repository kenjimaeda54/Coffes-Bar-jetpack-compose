package com.example.coffesbarcompose.models

data class OrdersByUserModel(
    val _id: String? = null,
    var orders: List<Orders>,
    val priceCartTotal: String,
    val tax: String,
    val userId: String
)


data class Orders(
    val _id: String? = null,
    val title: String,
    val price: String,
    var quantity: Int,
    val urlImage: String,
    val coffeeId: String
)