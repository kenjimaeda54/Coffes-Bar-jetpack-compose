package com.example.coffesbarcompose.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffesbarcompose.ui.theme.fontsInter

@Composable
fun ButtonCustomOutline(modifier: Modifier = Modifier, action: () -> Unit, text: String) {
    Column(modifier = modifier.clickable { action.invoke() }) {
        Text(
            text, style = TextStyle(
                fontFamily = fontsInter,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.outline.copy(0.5f),
            ), modifier = Modifier.padding(bottom = 5.dp)
        )
        Divider(modifier = Modifier.height(0.5.dp), color = MaterialTheme.colorScheme.outline.copy(0.5f))
    }
}

@Composable
@Preview
fun ButtonWithChildrenPreview() {
    ButtonCustomOutline(action = {}, text = "Clica aqui")
}