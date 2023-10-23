package com.example.coffesbarcompose.mocks

import com.example.coffesbarcompose.models.Orders
import com.example.coffesbarcompose.models.OrdersByUserModel

val favoritesMock = listOf(
    OrdersByUserModel(
        _id = "64d3847f106302ae76bef725",
        orders = listOf(
            Orders(
                _id = "64d0d735d545e3b60f6be839",
                title = "Pingado",
                price = "R$ 6,00",
                quantity = 3,
                urlImage = "https://cdn.abrahao.com.br/base/f4d/0e2/e7f/pingado.jpg",
                coffeeId = "dfdfd34343fdsdfw"
            ),
            Orders(
                _id = "64d0d735d545e3b60fsfsfs34346be839",
                title = "Bebida aleatoria",
                price = "R$ 6,30",
                quantity = 3,
                urlImage = "https://cdn.abrahao.com.br/base/f4d/0e2/e7f/pingado.jpg",
                coffeeId = "dfdfd34343ererefdsdfw"
            )
        ),
        priceCartTotal = "R$14,00",
        tax = "R$2,00",
        userId = "64d2c46cc5e48e0957f5c196"
    ),

    OrdersByUserModel(
        _id = "64d3847f10633434343sfksnofnsofnso02ae76bef725",
        orders = listOf(
            Orders(
                _id = "64d0d735d54f5e3b60f6be839",
                title = "Pingado",
                price = "R$ 6,00",
                quantity = 3,
                urlImage = "https://cdn.abrahao.com.br/base/f4d/0e2/e7f/pingado.jpg",
                coffeeId = "dfdfd34343fdfsfsfssdfw"
            ),
            Orders(
                _id = "64d0d735d545e3b60fsfsfs34346be839",
                title = "Bebida aleatoria",
                price = "R$ 6,30",
                quantity = 3,
                urlImage = "https://cdn.abrahao.com.br/base/f4d/0e2/e7f/pingado.jpg",
                coffeeId = "dfdfd34343fdsdfw"
            )
        ),
        priceCartTotal = "R$22,00",
        tax = "R$2,00",
        userId = "64d2c46cc5e48e0957f5c196"
    ),
)