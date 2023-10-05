package com.example.coffesbarcompose.screen.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.coffesbarcompose.ui.theme.fontsInter
import com.example.coffesbarcompose.ui.theme.fontsPacifico

@Composable
fun HomeScreen() {
    Text(text = "HomeScreen", fontFamily = fontsPacifico, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.outline)
}