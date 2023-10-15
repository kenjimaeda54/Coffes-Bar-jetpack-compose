package com.example.coffesbarcompose.screen.home

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.coffesbarcompose.view.ComposableLifecycle
import com.example.coffesbarcompose.view_models.CoffeesViewModel

@Composable
fun HomeFragment(navController: NavController,coffeesViewModel: CoffeesViewModel) {

//    ComposableLifecycle { _, event ->
//        if (event == Lifecycle.Event.ON_CREATE) {
//            coffeesViewModel.getAllCoffees()
//            Log.d("data","${coffeesViewModel.data.value.toString().count()}")
//        }
//    }
   Text(text = "ola")


}