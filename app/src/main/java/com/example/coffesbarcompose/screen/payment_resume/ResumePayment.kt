package com.example.coffesbarcompose.screen.payment_resume

import android.app.Activity
import android.widget.Toast
import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.models.AddressUserModel
import com.example.coffesbarcompose.route.StackScreensApp
import com.example.coffesbarcompose.view.ButtonCommon
import com.example.coffesbarcompose.view.ButtonCustomOutline
import com.example.coffesbarcompose.view.CustomOutlineTextField
import com.example.coffesbarcompose.view.RowTitleAndSubTitle
import com.example.coffesbarcompose.view_models.UserViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random


//https://github.com/velmurugan-murugesan/JetpackCompose/blob/master/ObserveCurrentLocationJetpackCompose/app/src/main/java/com/example/observecurrentlocationjetpackcompose/MainActivity.kt

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PaymentResume(
    navController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val deliveryFee = Random.nextDouble(3.0, 6.0)
    var isLoading by remember {
        mutableStateOf(false)
    }
    var addressStreet by remember {
        mutableStateOf("")
    }
    var addressCity by remember {
        mutableStateOf("")
    }
    var addressStreetNumber by remember {
        mutableStateOf("")
    }
    var addressNeighborhood by remember {
        mutableStateOf("")
    }
    var currentField by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf(AddressUserModel(streetNumber = "", street = "", neighBoard = "", city = ""))
    }
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )


    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    //quando usuario cliar no botao de voltar
    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }


    val launcherMultiplePermissions =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { it ->
            val areGranted = it.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                isLoading = true
                userViewModel.getLocation(activity, context) { geocoding ->
                    if (geocoding.data.toString().isNotEmpty()) {
                        val results = geocoding.data?.results?.get(0)
                        val newAddress = AddressUserModel(
                            city = results?.area ?: "",
                            neighBoard = results?.neighborhood ?: "",
                            streetNumber = results?.house ?: "",
                            street = results?.street ?: ""
                        )
                        address = newAddress
                        isLoading = false
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Nos precisamos da permissão para acessar localização",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    fun handleLocation() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        launcherMultiplePermissions.launch(permissions)

    }


    fun handleSheetAddress(field: String) {
        coroutineScope.launch {
            if (modalSheetState.isVisible) {
                modalSheetState.hide()
            } else {
                modalSheetState.show()
                currentField = field

            }
        }
    }

    fun handleNavigate() {
        navController.navigate(StackScreensApp.PaymentFinished.name)
    }


    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(12.dp),
        sheetContent = {

            Column(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (currentField) {
                    "street" -> CustomOutlineTextField(
                        placeHolder = address.street,
                        value = addressStreet,
                        onValueChange = { text -> addressStreet = text },
                        actionKeyboard = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        })

                    "streetNumber" -> CustomOutlineTextField(
                        placeHolder = address.streetNumber,
                        value = addressStreetNumber,
                        onValueChange = { text -> addressStreetNumber = text }, actionKeyboard = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        })

                    "neighBoard" -> CustomOutlineTextField(
                        placeHolder = address.neighBoard,
                        value = addressNeighborhood,
                        onValueChange = { text -> addressNeighborhood = text }, actionKeyboard = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        })

                    else -> CustomOutlineTextField(
                        placeHolder = address.city,
                        value = addressCity,
                        onValueChange = { text -> addressCity = text },
                        actionKeyboard = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        })
                }


            }
        }) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp, start = 12.dp, top = 50.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonCustomOutline(
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        action = {
                            handleSheetAddress(
                                field = "street"
                            )
                        },
                        text = addressStreet.ifEmpty { address.street.ifEmpty { "Rua" } }
                    )
                    ButtonCustomOutline(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        action = { handleSheetAddress(field = "streetNumber") },
                        text = addressStreetNumber.ifEmpty { address.streetNumber.ifEmpty { "Numero" } }
                    )
                }
                ButtonCustomOutline(
                    action = { handleSheetAddress(field = "neighBoard") },
                    text = addressNeighborhood.ifEmpty { address.neighBoard.ifEmpty { "Bairro" } })
                ButtonCustomOutline(
                    modifier = Modifier.padding(bottom = 10.dp),
                    action = { handleSheetAddress(field = "city") },
                    text = addressCity.ifEmpty { address.city.ifEmpty { "Cidade" } }
                )

                Surface(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.tertiary
                ) {
                    if (isLoading) CircularProgressIndicator(
                        modifier = Modifier
                            .padding(all = 3.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 1.dp
                    ) else Image(
                        modifier = Modifier
                            .padding(all = 2.dp)
                            .clickable { handleLocation() },
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "Icon Location",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                RowTitleAndSubTitle(
                    tile = "Taxa de entrega",
                    subTitle = "R$ formatDeliveryFree"
                )
                RowTitleAndSubTitle(tile = "Valor", subTitle = "R$ 35,00")
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                RowTitleAndSubTitle(tile = "Total", subTitle = "$ 35,00")
                ButtonCommon(
                    modifier = Modifier.padding(bottom = 24.dp, top = 10.dp),
                    title = "Tudo certo",
                    action = { handleNavigate() })

            }
        }
    }


}


@Composable
@Preview
fun ConfirmPaymentScreenPreview() {

}