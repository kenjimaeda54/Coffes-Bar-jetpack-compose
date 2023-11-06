package com.example.coffesbarcompose.screen.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.models.UserLoginModel
import com.example.coffesbarcompose.route.BottomBarScreen
import com.example.coffesbarcompose.route.StackScreensApp
import com.example.coffesbarcompose.ui.theme.fontsInter
import com.example.coffesbarcompose.ui.theme.fontsPacifico
import com.example.coffesbarcompose.view.ButtonCommon
import com.example.coffesbarcompose.view.ButtonCustomOutline
import com.example.coffesbarcompose.view.ButtonCustomOutlineSecurity
import com.example.coffesbarcompose.view.CustomOutlineTextField
import com.example.coffesbarcompose.view_models.UserViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LogInScreen(usersViewModel: UserViewModel = hiltViewModel(), navController: NavHostController) {
    val width = LocalConfiguration.current.screenWidthDp * 0.3

    val sheetValue = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var currentTextField by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
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

    val isLoading by remember(usersViewModel.dataUser.value.isLoading) {
        mutableStateOf(usersViewModel.dataUser.value.isLoading)
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


    fun handleCurrentTextField(field: String) {
        currentTextField = field
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


    fun handleLogin() {
        val login = UserLoginModel(email, password)
        usersViewModel.loginUser(login) {
            if (it.data != null) {
                navController.navigate(BottomBarScreen.Home.route) {
                    popUpTo(StackScreensApp.Login.name) {
                        inclusive = true
                    }
                }
            } else {
                errorMessage = "Email ou senha incorretos"
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetValue,
        sheetShape = RoundedCornerShape(12.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.secondary,
        sheetContent = {

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
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
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
                        title = "Entrar",
                        action = { handleLogin() },
                        enable = isValidPassword && isValidEmail,
                        feedbackLoading = it
                    )
                }

                Text(
                    modifier = Modifier
                        .clickable { navController.navigate(StackScreensApp.SignIn.name) }
                        .padding(vertical = 10.dp),
                    text = "Não possui registro clique aqui",
                    style = TextStyle(
                        fontFamily = fontsInter,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
        }
    }
}


