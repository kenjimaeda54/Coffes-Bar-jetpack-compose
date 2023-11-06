package com.example.coffesbarcompose.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.material3.IconButtonDefaults
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.mocks.coffeesMock
import com.example.coffesbarcompose.models.AvatarModel
import com.example.coffesbarcompose.models.UpdateAvatarModel
import com.example.coffesbarcompose.route.StackScreensApp
import com.example.coffesbarcompose.ui.theme.fontsPacifico
import com.example.coffesbarcompose.view.AvatarPlaceHolder
import com.example.coffesbarcompose.view.ButtonWithIcon
import com.example.coffesbarcompose.view.ComposableLifecycle
import com.example.coffesbarcompose.view.IconPlaceHolder
import com.example.coffesbarcompose.view.RowCoffee
import com.example.coffesbarcompose.view.RowCoffeePlaceHolder
import com.example.coffesbarcompose.view.TitlePlaceHolder
import com.example.coffesbarcompose.view_models.AvatarViewModel
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
    cartViewModel: CartViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    avatarViewModel: AvatarViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val rowWidth = LocalConfiguration.current.screenWidthDp * 0.42
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )

    val painterAvatar = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarViewModel.userAvatar.value.data?.urlAvatar)
            .build(),
    )


    var isLoadingImageAvatar by remember(painterAvatar) {
        mutableStateOf(false)
    }

    if (painterAvatar.state is AsyncImagePainter.State.Loading) {
        isLoadingImageAvatar = true
    }
    if (painterAvatar.state is AsyncImagePainter.State.Success) {
        isLoadingImageAvatar = false
    }

    val width = LocalConfiguration.current.screenWidthDp * 0.3

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            coffeesViewModel.getAllCoffees()
            avatarViewModel.getAllAvatars()
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    fun handleGoOut() {
        userViewModel.goOutApp()
        navController.navigate(StackScreensApp.Login.name)

    }

    fun handleUpdateAvatar(avatar: AvatarModel) {
        coroutineScope.launch {
            sheetState.hide()
        }
        val id = userViewModel.dataUser.value.data!!._id
        val updateAvatarModel = UpdateAvatarModel(avatarId = avatar._id)
        if (id != null) {
            avatarViewModel.updateAvatarUser(id, updateAvatarModel)
        }
    }

    if (coffeesViewModel.data.value.isLoading == true || coffeesViewModel.data.value.exception != null) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
            Column(modifier = Modifier.padding(all = 20.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitlePlaceHolder()
                    Row(verticalAlignment = Alignment.Bottom) {
                        AvatarPlaceHolder()
                        IconPlaceHolder()
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.FixedSize(rowWidth.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(coffeesMock) {
                        RowCoffeePlaceHolder(modifier = Modifier.padding(bottom = 10.dp))
                    }
                }
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
                    if (avatarViewModel.dataAvatars.value.data != null) {
                        items(avatarViewModel.dataAvatars.value.data!!) {
                            if (isLoadingImageAvatar) {
                                AvatarPlaceHolder()
                            } else {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable { handleUpdateAvatar(it) },
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(it.urlAvatar)
                                        .build(),
                                    contentDescription = "avatar image"
                                )
                            }

                        }
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
                        Row(verticalAlignment = Alignment.Bottom) {
                            Image(
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
                                painter = painterAvatar,
                                contentDescription = " Image avatar user"
                            )
                            Image(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable { handleGoOut() },
                                painter = painterResource(id = R.drawable.power),
                                contentDescription = "Out App",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
                            )
                        }

                    }
                    LazyVerticalGrid(
                        columns = GridCells.FixedSize(rowWidth.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(coffeesViewModel.data.value.data!!) {
                            RowCoffee(
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        StackScreensApp.DetailsScreen.name + "/${it._id}"
                                    )
                                },
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