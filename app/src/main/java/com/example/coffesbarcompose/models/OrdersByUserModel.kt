package com.example.coffesbarcompose.models

data class OrdersByUserModel(
    val _id: String,
    val orders: List<Orders>,
    val priceCartTotal: String,
    val tax: String,
    val userId: String
)


data class Orders(
    val _id: String,
    val title: String,
    val price: String,
    val quantity: Int,
    val urlImage: String,
    val coffeeId: String
)