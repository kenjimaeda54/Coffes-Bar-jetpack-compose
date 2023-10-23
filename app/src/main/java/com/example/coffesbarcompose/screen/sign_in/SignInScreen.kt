package com.example.coffesbarcompose.screen.sign_in

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.mocks.mockAvatar
import com.example.coffesbarcompose.ui.theme.fontsPacifico
import com.example.coffesbarcompose.view.ButtonCustomOutline
import com.example.coffesbarcompose.view.CustomOutlineTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignIn() {
    val width = LocalConfiguration.current.screenWidthDp * 0.3

    val sheetValue = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        coroutineScope.launch {
            if (sheetValue.isVisible) {
                sheetValue.hide()
            }
        }
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var currentTextField by remember {
        mutableStateOf("")
    }
    var isModalAvatar by remember {
        mutableStateOf(false)
    }

    fun handleCurrentTextField(field: String) {
        currentTextField = field
        isModalAvatar = false
        coroutineScope.launch {
            sheetValue.show()
        }
    }

    fun handleSheetAvatar() {
        isModalAvatar = true
        coroutineScope.launch {
            sheetValue.show()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetValue,
        sheetShape = RoundedCornerShape(12.dp),
        sheetBackgroundColor = if (isModalAvatar) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondary,
        sheetContent = {
            if (isModalAvatar) {
                LazyVerticalGrid(
                    columns = GridCells.FixedSize(width.dp),
                    contentPadding = PaddingValues(
                       all = 10.dp
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

            } else {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 15.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (currentTextField) {
                        "email" -> CustomOutlineTextField(
                            placeHolder = "Coloque seu email",
                            value = email,
                            onValueChange = { text -> email = text })

                        "password" -> CustomOutlineTextField(
                            placeHolder = "Coloque sua senha",
                            value = password,
                            onValueChange = { text -> password = text },
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }
                }
            }

        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier.padding(
                    end = 20.dp,
                    start = 20.dp,
                    bottom = 20.dp,
                    top = 50.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 30.dp),
                    text = "Coffees Bar", style = TextStyle(
                        fontFamily = fontsPacifico,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )

                )
                AsyncImage(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            handleSheetAvatar()
                        },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://firebasestorage.googleapis.com/v0/b/uploadimagesapicoffee.appspot.com/o/avatar06.png?alt=media&token=0830f591-9855-4c85-8368-0b89c9af6182")
                        .build(),
                    contentDescription = "Image Avatar",
                    contentScale = ContentScale.Fit,
                )
                ButtonCustomOutline(
                    action = { handleCurrentTextField("email") },
                    text = "Email"
                )
                Spacer(
                    modifier = Modifier.height(
                        20.dp
                    )
                )
                ButtonCustomOutline(
                    action = { handleCurrentTextField("password") },
                    text = "Password"
                )
            }
        }
    }
}


@Composable
@Preview
fun SignInPreview() {
    SignIn()
}