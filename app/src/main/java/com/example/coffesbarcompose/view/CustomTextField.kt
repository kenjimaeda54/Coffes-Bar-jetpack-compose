package com.example.coffesbarcompose.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(text: TextFieldValue,action: (TextFieldValue) -> Unit, placeholder:  @Composable() (() -> Unit)) {
    TextField(value = text, onValueChange = action, placeholder = placeholder)
}


@Composable
@Preview
fun CustomTextFieldPreview() {
    val text by rememberSaveable {
        mutableStateOf(TextFieldValue(""))
    }
    CustomTextField(text = text, action = {}, placeholder = {Text("Insira algo")})
}