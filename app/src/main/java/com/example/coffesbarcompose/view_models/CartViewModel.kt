package com.example.coffesbarcompose.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.models.CreateCartModel
import com.example.coffesbarcompose.models.Orders
import com.example.coffesbarcompose.models.OrdersByUserModel
import com.example.coffesbarcompose.models.UserCacheViewModel
import com.example.coffesbarcompose.repository.CoffeesBarRepository
import com.example.coffesbarcompose.utility.Format
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    userCacheViewModel: UserCacheViewModel,
    private val coffeesBarRepository: CoffeesBarRepository
) :
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


    private fun returnPriceCartAndTax(orders: List<Orders>): Pair<String,String> {
        val priceTotal =
            orders.map { it.price.split("R$")[1].replace(",", ".").toDouble() * it.quantity }
                .reduce { acc, d -> acc + d }
        val tax =   priceTotal * 0.10
        return Format.formatDoubleToMoneyReal(priceTotal) to Format.formatDoubleToMoneyReal(tax)
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

       val (priceCartTotal,tax) = returnPriceCartAndTax(orders)


        orderCart.value =
            OrdersByUserModel(
                _id = UUID.randomUUID().toString(),
                orders = orders,
                priceCartTotal = priceCartTotal,
                tax = tax,
                userId = user!!._id!!
            )

    }


    fun handleRemoveToCart(order: Orders) {
        val orders: List<Orders> = orderCart.value.orders.filter { it.coffeeId != order.coffeeId }
        coffeesAdded.removeIf { it._id == order.coffeeId }
        val (priceCartTotal) = returnPriceCartAndTax(orders)
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
                priceCartTotal = priceCartTotal,
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

        val (priceCartTotal) = returnPriceCartAndTax(newOrder)


        orderCart.value = OrdersByUserModel(
            _id = orderCart.value._id,
            orders = newOrder,
            priceCartTotal = priceCartTotal,
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
        val (priceCartTotal) = returnPriceCartAndTax(newOrder)
        orderCart.value = OrdersByUserModel(
            _id = orderCart.value._id,
            orders = newOrder,
            priceCartTotal = priceCartTotal,
            tax = orderCart.value.tax,
            userId = user!!._id!!
        )
    }


    fun createCart(ordersByUserModel: OrdersByUserModel) {
        val newOrders = ordersByUserModel.orders.map {
            Orders(
                title = it.title,
                price = it.price,
                quantity = it.quantity,
                urlImage = it.urlImage,
                coffeeId = it.coffeeId
            )
        }
        val cart = CreateCartModel(
            cart = OrdersByUserModel(
                orders = newOrders,
                priceCartTotal = ordersByUserModel.priceCartTotal,
                tax = ordersByUserModel.tax,
                userId = ordersByUserModel.userId
            )
        )
        viewModelScope.launch {
            coffeesBarRepository.createCart(createCartModel = cart)
        }
    }


}