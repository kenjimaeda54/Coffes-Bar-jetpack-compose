package com.example.coffesbarcompose.screen.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.mocks.mockAvatar
import com.example.coffesbarcompose.route.StackScreensApp
import com.example.coffesbarcompose.ui.theme.fontsPacifico
import com.example.coffesbarcompose.view.ButtonWithIcon
import com.example.coffesbarcompose.view.ComposableLifecycle
import com.example.coffesbarcompose.view.RowCoffee
import com.example.coffesbarcompose.view_models.CartViewModel
import com.example.coffesbarcompose.view_models.CoffeesViewModel
import com.example.coffesbarcompose.view_models.UserViewModel
import kotlinx.coroutines.launch


//ciclos de vida https://medium.com/@mohammadjoumani/life-cycle-in-jetpack-compose-2e96136ab936

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    coffeesViewModel: CoffeesViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = viewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val rowWidth = LocalConfiguration.current.screenWidthDp * 0.42
    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            coffeesViewModel.getAllCoffees()
            Log.d("user","${userViewModel.dataUser}")
        }
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )

    val width = LocalConfiguration.current.screenWidthDp * 0.3


    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }


    if (coffeesViewModel.data.value.isLoading == true || coffeesViewModel.data.value.exception != null) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
            Column(
                modifier = Modifier
                    .padding(all = 20.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
            }
        }
    } else {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
            sheetShape = RoundedCornerShape(12.dp),
            sheetContent = {
                LazyVerticalGrid(
                    columns = GridCells.FixedSize(width.dp),
                    contentPadding = PaddingValues(
                        end = 10.dp,
                        start = 10.dp,
                        bottom = 60.dp,
                        top = 10.dp
                    )
                ) {
                    items(mockAvatar) {
                        AsyncImage(
                            modifier = Modifier.size(50.dp),
                            model = ImageRequest.Builder(LocalContext.current).data(it).build(),
                            contentDescription = "avatar image"
                        )
                    }
                }
            }
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
                Column(modifier = Modifier.padding(all = 20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                                    fontFamily = fontsPacifico,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 30.sp
                                )
                            ) {
                                append("Coffees \n \n")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontFamily = fontsPacifico,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 40.sp
                                )
                            ) {
                                append("Bar \n")
                            }
                        })
                        AsyncImage(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(60.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        if (sheetState.isVisible) {
                                            sheetState.hide()
                                        } else {
                                            sheetState.show()
                                        }
                                    }
                                },
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://firebasestorage.googleapis.com/v0/b/uploadimagesapicoffee.appspot.com/o/avatar01.png?alt=media&token=4a3820fa-b757-4bcd-b148-1cd914956112")
                                .build(), contentDescription = " Image avatar user"
                        )
                    }
                    LazyVerticalGrid(
                        columns = GridCells.FixedSize(rowWidth.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(coffeesViewModel.data.value.data!!) {
                            RowCoffee(
                                modifier = Modifier.clickable { navController.navigate(StackScreensApp.DetailsScreen.name + "/${it._id}") },
                                coffee = it,
                                children = {
                                    ButtonWithIcon(
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = if (cartViewModel.coffeesAdded.contains(
                                                    it
                                                )
                                            ) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                                        ),
                                        colorFilter = ColorFilter.tint(
                                            if (cartViewModel.coffeesAdded.contains(
                                                    it
                                                )
                                            ) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                                        ),
                                        icon = painterResource(
                                            id = if (cartViewModel.coffeesAdded.contains(
                                                    it
                                                )
                                            ) R.drawable.minus else R.drawable.pluss
                                        ),
                                        action = { cartViewModel.addedOrRemoveToCart(it) })
                                },
                                isAdded = cartViewModel.coffeesAdded.contains(it)
                            )

                        }
                    }


                }
            }
        }

    }


}