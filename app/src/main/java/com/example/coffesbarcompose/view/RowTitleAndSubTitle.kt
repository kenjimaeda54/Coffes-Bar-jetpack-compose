package com.example.coffesbarcompose.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.coffesbarcompose.ui.theme.fontsInter

@Composable
fun RowTitleAndSubTitle(modifier: Modifier = Modifier, tile: String, subTitle: String) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            tile,
            style = TextStyle(
                fontFamily = fontsInter,
                fontWeight = FontWeight.Light,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
        )
        Text(
            subTitle, style = TextStyle(
                fontFamily = fontsInter,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        )
    }
}

@Composable
@Preview
fun RowTitleAndSubTitlePreview() {
    RowTitleAndSubTitle(tile = "Taxa de entrega", subTitle = "R$ 34,00")
}