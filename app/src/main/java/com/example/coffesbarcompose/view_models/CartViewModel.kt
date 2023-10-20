package com.example.coffesbarcompose.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.coffesbarcompose.models.CoffeesModel

class CartViewModel: ViewModel() {
   var coffeesAdded = mutableStateListOf<CoffeesModel>()


    fun addedOrRemoveToCart(coffeesModel: CoffeesModel) {
         if(coffeesAdded.contains(coffeesModel)) coffeesAdded.remove(coffeesModel) else coffeesAdded.add(coffeesModel)

    }

}