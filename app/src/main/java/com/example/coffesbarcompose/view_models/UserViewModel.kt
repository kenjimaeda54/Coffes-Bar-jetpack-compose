package com.example.coffesbarcompose.view_models

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffesbarcompose.data.DataOrException
import com.example.coffesbarcompose.models.AvatarModel
import com.example.coffesbarcompose.models.GeoCodingModel
import com.example.coffesbarcompose.models.UpdateAvatarModel
import com.example.coffesbarcompose.models.UserCacheViewModel
import com.example.coffesbarcompose.models.UserLoginModel
import com.example.coffesbarcompose.models.UserModel
import com.example.coffesbarcompose.repository.CoffeesBarRepository
import com.example.coffesbarcompose.repository.GeoCodingRepository
import com.example.coffesbarcompose.services.CoffeesBarServiceApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//compartilhar dados no kotlin
//https://www.youtube.com/watch?v=h61Wqy3qcKg
//https://github.com/philipplackner/SharingDataBetweenScreens
//https://tomas-repcik.medium.com/locationrequest-create-got-deprecated-how-to-fix-it-e4f814138764
//https://github.com/velmurugan-murugesan/JetpackCompose/blob/master/ObserveCurrentLocationJetpackCompose/app/src/main/java/com/example/observecurrentlocationjetpackcompose/MainActivity.kt
@HiltViewModel
class UserViewModel @Inject constructor(
    private val geoCodingRepository: GeoCodingRepository,
    private val coffeesBarRepository: CoffeesBarRepository,
    private val userCacheViewModel: UserCacheViewModel

) :
    ViewModel() {
    private var dataAddressUser: MutableState<DataOrException<GeoCodingModel, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )
    private var fusedLocationClient: FusedLocationProviderClient? = null

    var isAnonymous = mutableStateOf(
        userCacheViewModel.getUser() == null
    )
    var dataUser: MutableState<DataOrException<UserModel, Boolean, Exception>> = mutableStateOf(
        DataOrException(userCacheViewModel.getUser(), false, Exception(""))
    )



    init {
        val userCache = userCacheViewModel.getUser()

    }


    fun getLocation(
        activity: Activity,
        context: Context,
        completion: (DataOrException<GeoCodingModel, Boolean, Exception>) -> Unit
    ) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            //https://stackoverflow.com/questions/45012289/java-io-ioexception-grpc-failed

            fusedLocationClient!!.lastLocation.addOnSuccessListener {


                viewModelScope.launch {
                    dataAddressUser.value.isLoading = true
                    completion(geoCodingRepository.getAddress(location = "${it.latitude},${it.longitude}"))
                    if (dataAddressUser.value.toString().isNotEmpty()) {
                        dataAddressUser.value.isLoading = false
                    }
                }

                //
//                @RequiresApi(Build.VERSION_CODES.TIRAMISU)

//                //precisa ser a versao tiramisu
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
//
//                    Geocoder(context).getFromLocation(it.latitude, it.longitude, 3) { address ->
//                        val firstAddress = address.first()
//                        Log.d("subAdmin", "${firstAddress.subAdminArea}") // cidade
//                        Log.d("subThroughfare", "${firstAddress.subThoroughfare}") // numero da rua
//                        Log.d("adminArea", "${firstAddress.subLocality}") // bairro
//                        Log.d("subLocality", "${firstAddress.subLocality}")
//                        Log.d("tourhfare", "${firstAddress.thoroughfare}") //returna nome da rua
//                        Log.d("", firstAddress.locality)
//                    }
//                } else {
//                    Toast.makeText(
//                        context,
//                        "Versão mínima para usar e a 33 precisa atualizar celular",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }


            }
        }

    }


    fun createUser(
        user: UserModel,
        completion: (data: DataOrException<UserModel?, Boolean, Exception>) -> Unit
    ) {
        viewModelScope.launch {
            dataUser.value.isLoading = true
            val response = coffeesBarRepository.createUser(user)
            dataUser.value.isLoading = response.isLoading

            if (response.data != null) {
                userCacheViewModel.saveUser(response.data!!)
                completion(DataOrException(data = response.data))
            } else {
                completion(DataOrException(data = null))
            }

        }
    }


    fun loginUser(
        userLoginModel: UserLoginModel,
        completion: (data: DataOrException<UserModel?, Boolean, Exception>) -> Unit
    ) {
        viewModelScope.launch {
            dataUser.value.isLoading = true
            val response = coffeesBarRepository.loginUser(userLoginModel)
            dataUser.value.isLoading = response.isLoading

            if (response.data != null) {
                userCacheViewModel.saveUser(response.data!!)
                completion(DataOrException(data = response.data))
            } else {
                completion(DataOrException(data = null))
            }
        }
    }



    fun goOutApp() {
        userCacheViewModel.clearUser()
    }


}