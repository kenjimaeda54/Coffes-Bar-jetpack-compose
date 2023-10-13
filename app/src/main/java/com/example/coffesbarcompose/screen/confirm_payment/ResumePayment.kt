package com.example.coffesbarcompose.screen.confirm_payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.coffesbarcompose.view.CustomTextField

@Composable
fun ResumePayment() {
    val textDistrict by rememberSaveable {
        mutableStateOf(TextFieldValue(""))
    }

}


@Composable
@Preview
fun ConfirmPaymentScreenPreview() {

}