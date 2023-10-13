package com.example.coffesbarcompose.mocks

import com.example.coffesbarcompose.models.Orders
import com.example.coffesbarcompose.models.OrdersByUserModel

val ordersByUserMock: List<Orders> = listOf(
    Orders(
        _id = "64d0d735d545e3b60f6be839",
        title = "Pingado",
        price = "R$ 6,00",
        quantity = 3,
        urlImage = "https://cdn.abrahao.com.br/base/f4d/0e2/e7f/pingado.jpg",
        coffeeId = "d0d782cc315bcb623ed940"
    ),
    Orders(
        _id = "d0d782cc315bcb623ed940",
        title = "Latte",
        price = "R$ 14,00",
        quantity = 4,
        urlImage = "https://cdn.abrahao.com.br/base/f50/c70/35e/latte.jpg",
        coffeeId = "d0d782cc315bcb623ed940"
    ),
    Orders(
        _id = "64d3847f106302ae76befsf5sf726",
        title = "Bebida aleatoria",
        price = "R$ 6,30",
        quantity = 7,
        urlImage = "https://cdn.abrahao.com.br/base/fa7/518/562/espresso.jpg",
        coffeeId = "d0d782cc315bcb623ed940"
    ),


)