package com.example.coffesbarcompose.screen.payment_resume

import android.app.Activity
import android.widget.Toast
import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.models.AddressUserModel
import com.example.coffesbarcompose.route.StackScreens
import com.example.coffesbarcompose.utility.Format
import com.example.coffesbarcompose.view.ButtonCommon
import com.example.coffesbarcompose.view.ButtonCustomOutline
import com.example.coffesbarcompose.view.RowTitleAndSubTitle
import com.example.coffesbarcompose.view_models.UserViewModel
import kotlin.random.Random


//https://github.com/velmurugan-murugesan/JetpackCompose/blob/master/ObserveCurrentLocationJetpackCompose/app/src/main/java/com/example/observecurrentlocationjetpackcompose/MainActivity.kt

@Composable
fun PaymentResume(
    navController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val deliveryFee = Random.nextDouble(3.0, 6.0)
    var isLoading by remember {
        mutableStateOf(false)
    }
    var address by remember {
        mutableStateOf(AddressUserModel(streetNumber = "", street = "", neighBoard = "", city = ""))
    }


    val context = LocalContext.current
    val activity = LocalContext.current as Activity


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
                    action = { /*TODO*/ },
                    text = address.street.ifEmpty { "Rua" }
                )
                ButtonCustomOutline(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    action = { /*TODO*/ },
                    text = address.streetNumber.ifEmpty { "Numero" }
                )
            }
            ButtonCustomOutline(
                action = { /*TODO*/ },
                text = address.neighBoard.ifEmpty { "Bairro" })
            ButtonCustomOutline(
                modifier = Modifier.padding(bottom = 10.dp),
                action = { /*TODO*/ },
                text = address.city.ifEmpty { "Cidade" }
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
                action = { navController.navigate(StackScreens.PaymentFinished.name) })

        }
    }


}


@Composable
@Preview
fun ConfirmPaymentScreenPreview() {

}