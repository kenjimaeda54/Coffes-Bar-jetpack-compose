package com.example.coffesbarcompose.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffesbarcompose.data.DataOrException
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
    private val coffeesBarRepository: CoffeesBarRepository,
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
    var allOrders: MutableState<DataOrException<List<OrdersByUserModel>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )
    private val allCoffees: MutableState<List<CoffeesModel>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            val response = coffeesBarRepository.getAllCoffees()
            if (response.data != null) {
                allCoffees.value = response.data!!

            }

        }
    }

    fun addedOrRemoveToCart(coffeesModel: CoffeesModel) {


        if (coffeesAdded.contains(coffeesModel)) {
            coffeesAdded.remove(coffeesModel)
            handleRemoveOrderToCart(coffeesModel._id)
        } else {
            coffeesAdded.add(
                coffeesModel
            )

            val haveOrder = orderCart.value.orders.firstOrNull { it.coffeeId == coffeesModel._id }

            if (haveOrder == null) {
                val order = Orders(
                    _id = UUID.randomUUID().toString(),
                    title = coffeesModel.name,
                    price = coffeesModel.price,
                    quantity = 1,
                    urlImage = coffeesModel.urlPhoto,
                    coffeeId = coffeesModel._id
                )
                val newListOrders = orderCart.value.orders.toMutableList()
                newListOrders.add(order)
                orderCart.value.orders = newListOrders
            }

            if (orderCart.value.orders.isNotEmpty()) {
                val (priceCartTotal, tax) = returnPriceCartAndTax(orderCart.value.orders)
                orderCart.value =
                    OrdersByUserModel(
                        _id = UUID.randomUUID().toString(),
                        orders = orderCart.value.orders,
                        priceCartTotal = priceCartTotal,
                        tax = tax,
                        userId = user!!._id!!
                    )

            }

        }


    }


    fun addToCartByFavorites(ordersByUser: OrdersByUserModel) {
        if (orderCart.value.orders.isEmpty()) {
            for (order in ordersByUser.orders) {
                orderCart.value = ordersByUser
                val newCoffee = allCoffees.value.first { it._id == order.coffeeId }
                coffeesAdded.add(newCoffee)
            }
            return
        }



        for (order in ordersByUser.orders) {


            val haveCoffee = coffeesAdded.firstOrNull { it._id == order.coffeeId }

            if (haveCoffee != null) {
                val newOrders = orderCart.value.orders.toMutableList().map {
                    if (haveCoffee._id == it.coffeeId) {
                        Orders(
                            _id = it._id,
                            coffeeId = it.coffeeId,
                            price = it.price,
                            urlImage = it.urlImage,
                            title = it.title,
                            quantity = it.quantity + order.quantity
                        )
                    } else {
                        Orders(
                            _id = it._id,
                            coffeeId = it.coffeeId,
                            price = it.price,
                            urlImage = it.urlImage,
                            title = it.title,
                            quantity = it.quantity
                        )
                    }

                }
                if (newOrders.isNotEmpty()) {
                    val (priceTotal, tax) = returnPriceCartAndTax(newOrders)
                    val newOrderByUser = OrdersByUserModel(
                        _id = ordersByUser._id,
                        orders = newOrders,
                        priceCartTotal = priceTotal,
                        tax = tax,
                        userId = ordersByUser.userId
                    )
                    orderCart.value = newOrderByUser
                }


            } else {
                val newListCoffee = orderCart.value.orders.toMutableList()
                newListCoffee.add(order)
                orderCart.value.orders = newListCoffee
                val newCoffee = allCoffees.value.first { it._id == order.coffeeId }
                val coffee = CoffeesModel(
                    _id = newCoffee._id,
                    description = newCoffee.description,
                    name = newCoffee.name,
                    price = newCoffee.price,
                    quantityMl = newCoffee.quantityMl,
                    urlPhoto = newCoffee.urlPhoto
                )
                coffeesAdded.add(coffee)
                if (orderCart.value.orders.isNotEmpty()) {
                    val (priceTotal, tax) = returnPriceCartAndTax(orderCart.value.orders)
                    val newOrderByUser = OrdersByUserModel(
                        _id = orderCart.value._id,
                        orders = orderCart.value.orders,
                        priceCartTotal = priceTotal,
                        tax = tax,
                        userId = orderCart.value.userId
                    )
                    orderCart.value = newOrderByUser
                }

            }

        }


    }

    private fun returnPriceCartAndTax(orders: List<Orders>): Pair<String, String> {

        val priceTotal =
            orders.map { it.price.split("R$")[1].replace(",", ".").toDouble() * it.quantity }
                .reduce { acc, d -> acc + d }
        val tax = priceTotal * 0.10
        return Format.formatDoubleToMoneyReal(priceTotal) to Format.formatDoubleToMoneyReal(tax)
    }


    fun handleRemoveOrderToCart(coffeeId: String) {
        val orders: List<Orders> = orderCart.value.orders.filter { it.coffeeId != coffeeId }
        coffeesAdded.removeIf { it._id == coffeeId }
        if (orders.isEmpty()) {
            orderCart.value = OrdersByUserModel(
                _id = "",
                orders = emptyList(),
                priceCartTotal = "",
                tax = "",
                userId = ""
            )

        } else {
            val (priceCartTotal) = returnPriceCartAndTax(orders)
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

    fun getOrder() {
        viewModelScope.launch {
            allOrders.value.isLoading = true
            allOrders.value = coffeesBarRepository.getOrders(user!!._id!!)
            allOrders.value.isLoading = false

        }
    }

}