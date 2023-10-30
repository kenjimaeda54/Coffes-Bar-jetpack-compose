package com.example.coffesbarcompose.view_models

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
class CartViewModel  @Inject constructor(private  val userCacheViewModel: UserCacheViewModel): ViewModel() {
   var coffeesAdded = mutableStateListOf<CoffeesModel>()

    fun addedOrRemoveToCart(coffeesModel: CoffeesModel) {

         if(coffeesAdded.contains(coffeesModel)) coffeesAdded.remove(coffeesModel) else coffeesAdded.add(coffeesModel)
    }

    fun returnListOrders(): List<OrdersByUserModel> {
        val user = userCacheViewModel.getUser()
        val orders: List<Orders> = coffeesAdded.map {  Orders(
            _id = UUID.randomUUID().toString(),
            title = it.name,
            price = it.price,
            quantity = 1,
            urlImage = it.urlPhoto,
            coffeeId = it._id
        ) }
       val priceTotal   = orders.map { it.price.split("R$")[1].replace(",",".").toDouble()}.reduce { acc, d -> acc + d }
       val tax = Random.nextDouble(3.0, 6.0)

        return listOf(
           OrdersByUserModel(
               _id = UUID.randomUUID().toString(),
               orders = orders,
               priceCartTotal = "R$ $priceTotal",
               tax = Format.formatDoubleToMoneyReal(tax),
               userId = user!!._id!!
           )
       )
    }
}