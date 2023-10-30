package com.example.coffesbarcompose.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffesbarcompose.ui.theme.fontsInter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonCustomOutlineSecurity(modifier: Modifier = Modifier, action: () -> Unit, text: String, visualTransformation: VisualTransformation) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = text,
        onValueChange = {},
        enabled = false,
        readOnly = true,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        textStyle = TextStyle(
            fontFamily = fontsInter,
            fontSize = 17.sp,
            color =  MaterialTheme.colorScheme.outline.copy(0.5f),
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None
        ),
        singleLine = false,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clickable { action() }
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = text,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = true,
            enabled = false,
            interactionSource = interactionSource,

            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 1.dp, bottom = 1.dp, end = 0.dp, start = 0.dp
            ),
        ){
            TextFieldDefaults.FilledContainerBox(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = MaterialTheme.colorScheme.outline.copy(0.5f),
                    textColor =  MaterialTheme.colorScheme.outline.copy(0.5f),
                    containerColor = Color.Transparent,
                ),
                enabled = false,
                interactionSource = interactionSource,
                isError = false,
            )
        }
    }
}

