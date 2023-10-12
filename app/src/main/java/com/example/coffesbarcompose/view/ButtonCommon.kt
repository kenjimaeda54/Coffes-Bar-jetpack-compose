package com.example.coffesbarcompose.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffesbarcompose.ui.theme.fontsInter

@Composable
fun ButtonCommon() {
    Button(modifier = Modifier
        .fillMaxWidth().height(31.dp),
        shape = RoundedCornerShape(7.dp),
        contentPadding = PaddingValues(all = 3.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        onClick = { /*TODO*/ }) {
        Text(
           text = "Comprar",
            style = TextStyle(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                fontFamily = fontsInter,
                fontSize = 18.sp
            ),
        )
    }
}


@Composable
@Preview(showBackground = true)
fun ButtonCommonPreview() {
    ButtonCommon()
}