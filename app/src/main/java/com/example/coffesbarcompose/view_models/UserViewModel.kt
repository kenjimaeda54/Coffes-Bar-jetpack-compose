package com.example.coffesbarcompose.view_models

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffesbarcompose.data.DataOrException
import com.example.coffesbarcompose.models.AvatarModel
import com.example.coffesbarcompose.models.GeoCodingModel
import com.example.coffesbarcompose.models.UserModel
import com.example.coffesbarcompose.repository.CoffeesBarRepository
import com.example.coffesbarcompose.repository.GeoCodingRepository
import com.example.coffesbarcompose.services.CoffeesBarServiceApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//https://tomas-repcik.medium.com/locationrequest-create-got-deprecated-how-to-fix-it-e4f814138764
//https://github.com/velmurugan-murugesan/JetpackCompose/blob/master/ObserveCurrentLocationJetpackCompose/app/src/main/java/com/example/observecurrentlocationjetpackcompose/MainActivity.kt
@HiltViewModel
class UserViewModel @Inject constructor(
    private val geoCodingRepository: GeoCodingRepository,
    private val coffeesBarRepository: CoffeesBarRepository
) :
    ViewModel() {
    private var dataAddressUser: MutableState<DataOrException<GeoCodingModel, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )
    private var fusedLocationClient: FusedLocationProviderClient? = null
    var dataAvatars: MutableState<DataOrException<List<AvatarModel>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )
    var isAnonymous = mutableStateOf(true)
    var dataUser: MutableState<DataOrException<UserModel, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

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


    fun createUser(user: UserModel) {
        viewModelScope.launch {
            val response = coffeesBarRepository.createUser(user)

            if (response.data != null) {
                isAnonymous.value = false
                dataUser.value.data = response.data
            }

        }
    }

    fun getAllAvatars() {
        viewModelScope.launch {
            dataAvatars.value.isLoading = true

            dataAvatars.value = coffeesBarRepository.getAvatars()

            if (dataAvatars.toString().isNotEmpty()) {
                dataAvatars.value.isLoading = false
            }

        }
    }

}