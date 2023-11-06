package com.example.coffesbarcompose.screen.sign_in

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.models.AvatarModel
import com.example.coffesbarcompose.models.UserModel
import com.example.coffesbarcompose.route.StackScreensApp
import com.example.coffesbarcompose.ui.theme.fontsInter
import com.example.coffesbarcompose.ui.theme.fontsPacifico
import com.example.coffesbarcompose.view.ButtonCommon
import com.example.coffesbarcompose.view.ButtonCustomOutline
import com.example.coffesbarcompose.view.ButtonCustomOutlineSecurity
import com.example.coffesbarcompose.view.ComposableLifecycle
import com.example.coffesbarcompose.view.CustomOutlineTextField
import com.example.coffesbarcompose.view_models.AvatarViewModel
import com.example.coffesbarcompose.view_models.UserViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern


//-https://proandroiddev.com/jetpack-compose-visualtransformation-made-easier-c5192bde3f03
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignIn(
    usersViewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
    avatarViewModel: AvatarViewModel = hiltViewModel()
) {
    val width = LocalConfiguration.current.screenWidthDp * 0.3

    val sheetValue = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()

    var errorMessage by remember {
        mutableStateOf("")
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

    var visualTransformation by remember {
        mutableStateOf<VisualTransformation>(PasswordVisualTransformation())
    }

    val isValidEmail by remember(email) {
        mutableStateOf(
            if (email.isEmpty()) {
                false
            } else {
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        )

    }

    var dataAvatar by remember {
        mutableStateOf(
            AvatarModel(
                _id = "64d189bba57ef7a8ec728dcf",
                urlAvatar = "https://firebasestorage.googleapis.com/v0/b/uploadimagesapicoffee.appspot.com/o/avatar01.png?alt=media&token=4a3820fa-b757-4bcd-b148-1cd914956112"
            )
        )
    }

    val isValidPassword by remember(password) {
        mutableStateOf(
            if (password.isEmpty()) {
                false
            } else {
                val passwordREGEX = Pattern.compile(
                    "^" +
                            "(?=.*[0-9])" +         //at least 1 digit
                            "(?=.*[a-z])" +         //at least 1 lower case letter
                            "(?=.*[A-Z])" +         //at least 1 upper case letter
                            "(?=.*[a-zA-Z])" +      //any letter
                            "(?=.*[@#$%^&+=])" +    //at least 1 special character
                            "(?=\\S+$)" +           //no white spaces
                            ".{8,}" +               //at least 8 characters
                            "$"
                );
                passwordREGEX.matcher(password).matches()
            }
        )
    }

    BackHandler {
        coroutineScope.launch {
            if (sheetValue.isVisible) {
                sheetValue.hide()
            }
        }
    }

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            avatarViewModel.getAllAvatars()
        }
    }


    val isLoading by remember(usersViewModel.dataUser.value.isLoading) {
        mutableStateOf(usersViewModel.dataUser.value.isLoading)
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

    fun handleActionKeyBoard() {
        coroutineScope.launch {
            sheetValue.hide()
        }
    }

    fun handleVisualTransformation() {
        visualTransformation = if (visualTransformation == PasswordVisualTransformation()) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }

    }

    fun handleAvatarClicked(avatar: AvatarModel) {
        coroutineScope.launch {
            sheetValue.hide()
        }
        dataAvatar = avatar

    }


    fun handleRegisterUser() {
        val user =
            UserModel(name = "", email = email, password = password, avatarId = dataAvatar._id)
        usersViewModel.createUser(user) {
            if (it.data != null) {
                navController.navigate(StackScreensApp.MainScreen.name)
            } else {
                errorMessage = "Este email ja foi registrado na base"
            }
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
                    if (avatarViewModel.dataAvatars.value.data != null) {
                        items(avatarViewModel.dataAvatars.value.data!!) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable { handleAvatarClicked(it) },
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(it.urlAvatar).build(),
                                contentDescription = "avatar image"
                            )
                        }
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
                            onValueChange = { text -> email = text },
                            actionKeyboard = { handleActionKeyBoard() })

                        "password" -> CustomOutlineTextField(
                            placeHolder = "Coloque sua senha",
                            value = password,
                            onValueChange = { text -> password = text },
                            visualTransformation = visualTransformation,
                            actionKeyboard = { handleActionKeyBoard() },
                            singleLine = true,
                            leadingIcon = {
                                Image(
                                    modifier = Modifier
                                        .size(15.dp)
                                        .clickable {
                                            handleVisualTransformation()
                                        },
                                    painter = if (visualTransformation == PasswordVisualTransformation()) painterResource(
                                        id = R.drawable.lock
                                    ) else painterResource(id = R.drawable.padlock),
                                    contentDescription = "Icon represented password",
                                    contentScale = ContentScale.Fit,
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer)
                                )
                            }
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
                        .data(dataAvatar.urlAvatar)
                        .build(),
                    contentDescription = "Image Avatar",
                    contentScale = ContentScale.Fit,
                )
                Spacer(
                    modifier = Modifier.height(
                        20.dp
                    )
                )
                ButtonCustomOutline(
                    action = { handleCurrentTextField("email") },
                    text = email.ifEmpty { "Email" }
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (!isValidEmail && email.isNotEmpty()) "Email precisa ser valido" else errorMessage.ifEmpty { "" },
                    style = TextStyle(
                        fontFamily = fontsInter,
                        color = MaterialTheme.colorScheme.error.copy(0.6f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    ),
                    textAlign = TextAlign.Left
                )
                Spacer(
                    modifier = Modifier.height(
                        20.dp
                    )
                )
                ButtonCustomOutlineSecurity(
                    action = { handleCurrentTextField("password") },
                    text = password.ifEmpty { "Password" },
                    visualTransformation = if (password.isNotEmpty()) PasswordVisualTransformation() else VisualTransformation.None
                )
                Text(
                    text = if (!isValidPassword && password.isNotEmpty()) "Senha precisa conter caracter maisculo, especial e digito" else "",
                    style = TextStyle(
                        fontFamily = fontsInter,
                        color = MaterialTheme.colorScheme.error.copy(0.6f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                isLoading?.let {
                    ButtonCommon(
                        title = "Registrar",
                        action = { handleRegisterUser() },
                        enable = isValidPassword && isValidEmail,
                        feedbackLoading = it
                    )
                }

            }
        }
    }
}

