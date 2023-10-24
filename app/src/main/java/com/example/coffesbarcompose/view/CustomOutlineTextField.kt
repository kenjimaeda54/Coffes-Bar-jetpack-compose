package com.example.coffesbarcompose.view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffesbarcompose.ui.theme.fontsInter
import kotlin.math.sin

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CustomOutlineTextField(
    placeHolder: String,
    value: String,
    onValueChange: (text: String) -> Unit,
    actionKeyboard: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon:  @Composable() (() -> Unit)? = null,
    singleLine: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true
    val keyboardController = LocalSoftwareKeyboardController.current

    //abaixo como contornar altura dinamica no outlineTextField e tambem a largura do width
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        enabled = enabled,
        textStyle = TextStyle(
            fontFamily = fontsInter,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.primary
        ),
        keyboardActions = KeyboardActions(onDone = {
            actionKeyboard?.invoke()
            keyboardController?.hide()
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None
        ),
        singleLine = false,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(0.95f)
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            leadingIcon = leadingIcon,
            placeholder = {
                Text(
                    text = placeHolder, style = TextStyle(
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),

                        )
                )
            },
            singleLine = singleLine,
            enabled = enabled,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 4.dp, bottom = 4.dp, end = 8.dp, start = 8.dp
            ),
        ) {
            TextFieldDefaults.OutlinedBorderContainerBox(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                ),
                focusedBorderThickness = 1.dp,
                unfocusedBorderThickness = 1.dp
            )


        }
    }

}

