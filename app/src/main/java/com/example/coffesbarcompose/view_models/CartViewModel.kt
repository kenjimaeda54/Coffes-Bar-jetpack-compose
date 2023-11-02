package com.example.coffesbarcompose.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.models.Orders
import com.example.coffesbarcompose.models.OrdersByUserModel
import com.example.coffesbarcompose.models.UserCacheViewModel
import com.example.coffesbarcompose.utility.Format
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CartViewModel @Inject constructor(private val userCacheViewModel: UserCacheViewModel) :
    ViewModel() {
    var coffeesAdded = mutableStateListOf<CoffeesModel>()
    var orderCart = mutableStateOf(
        OrdersByUserModel(
            _id = "",
            orders = emptyList(),
            priceCartTotal = "",
            tax = "",
            userId = ""
        )
    )
    private val user = userCacheViewModel.getUser()

    fun addedOrRemoveToCart(coffeesModel: CoffeesModel) {

        if (coffeesAdded.contains(coffeesModel)) coffeesAdded.remove(coffeesModel) else coffeesAdded.add(
            coffeesModel
        )
    }


    private fun returnPriceCart(orders: List<Orders>): String {
        val priceTotal =
            orders.map { it.price.split("R$")[1].replace(",", ".").toDouble() * it.quantity }
                .reduce { acc, d -> acc + d }
        return Format.formatDoubleToMoneyReal(priceTotal)
    }

    fun handleOrderToCart() {


        val orders: List<Orders> = coffeesAdded.map {
            Orders(
                _id = UUID.randomUUID().toString(),
                title = it.name,
                price = it.price,
                quantity = 1,
                urlImage = it.urlPhoto,
                coffeeId = it._id
            )
        }

        val tax = Random.nextDouble(3.0, 6.0)

        orderCart.value =
            OrdersByUserModel(
                _id = UUID.randomUUID().toString(),
                orders = orders,
                priceCartTotal = returnPriceCart(orders),
                tax = Format.formatDoubleToMoneyReal(tax),
                userId = user!!._id!!
            )

    }


    fun handleRemoveToCart(order: Orders) {
        val orders: List<Orders> = orderCart.value.orders.filter { it.coffeeId != order.coffeeId }
        coffeesAdded.removeIf { it._id == order.coffeeId }
        if (orders.isEmpty()) {
            orderCart.value = OrdersByUserModel(
                _id = "",
                orders = emptyList(),
                priceCartTotal = "",
                tax = "",
                userId = ""
            )

        } else {
            orderCart.value = OrdersByUserModel(
                _id = orderCart.value._id,
                orders = orders,
                priceCartTotal = returnPriceCart(orders),
                tax = orderCart.value.tax,
                userId = orderCart.value.userId
            )

        }

    }

    fun handleAddQuantityToCart(order: Orders) {
        //em Koltin nao preciso usar a palavra retorno
        //so criar a classe transformada.
        //swfit precisa e typescript
        val newOrder: List<Orders> = orderCart.value.orders.map {
            if (it._id == order._id && it.quantity < 30) {
                val newValue = it.quantity + 1
                Orders(
                    _id = it._id,
                    quantity = newValue,
                    urlImage = it.urlImage,
                    coffeeId = it.coffeeId,
                    price = it.price,
                    title = it.title
                )
            } else {
                Orders(
                    _id = it._id,
                    quantity = it.quantity,
                    urlImage = it.urlImage,
                    coffeeId = it.coffeeId,
                    price = it.price,
                    title = it.title
                )
            }
        }

        orderCart.value = OrdersByUserModel(
            _id = orderCart.value._id,
            orders = newOrder,
            priceCartTotal = returnPriceCart(newOrder),
            tax = orderCart.value.tax,
            userId = user!!._id!!
        )

    }

    fun handleRemoveQuantityToCart(order: Orders) {
        val newOrder: List<Orders> = orderCart.value.orders.map {
            if (it._id == order._id && it.quantity > 1) {
                val newValue = it.quantity - 1
                Orders(
                    _id = it._id,
                    quantity = newValue,
                    urlImage = it.urlImage,
                    coffeeId = it.coffeeId,
                    price = it.price,
                    title = it.title
                )
            } else {
                Orders(
                    _id = it._id,
                    quantity = it.quantity,
                    urlImage = it.urlImage,
                    coffeeId = it.coffeeId,
                    price = it.price,
                    title = it.title
                )
            }
        }

        orderCart.value = OrdersByUserModel(
            _id = orderCart.value._id,
            orders = newOrder,
            priceCartTotal = returnPriceCart(newOrder),
            tax = orderCart.value.tax,
            userId = user!!._id!!
        )
    }
}