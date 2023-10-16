package com.example.coffesbarcompose.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffesbarcompose.data.DataOrException
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.repository.CoffeesBarRepository
import com.example.coffesbarcompose.services.CoffeesBarService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoffeesViewModel @Inject constructor(private  val coffeesBarRepository: CoffeesBarRepository): ViewModel() {
      val data: MutableState<DataOrException<List<CoffeesModel>,Boolean,Exception>> = mutableStateOf(
         DataOrException(null,true,Exception(""))
     )

    fun getAllCoffees()  {
        viewModelScope.launch {
            data.value.isLoading = true
            data.value = coffeesBarRepository.getAllCoffees()

            if(data.value.toString().isNotEmpty()) {
                data.value.isLoading = false
            }
        }

    }
    }