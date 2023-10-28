package com.example.coffesbarcompose.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coffesbarcompose.modifier.shimmerBackground


@Composable
fun RowCoffeePlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(220.dp)
            .width(150.dp)
            .shimmerBackground(RoundedCornerShape(5.dp))
    )
}

@Composable
fun TitlePlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(25.dp)
            .width(150.dp)
            .shimmerBackground(RoundedCornerShape(5.dp))
    )
}

@Composable
fun AvatarPlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(50.dp)
            .height(50.dp)
            .shimmerBackground(CircleShape)
    )

}

@Composable
fun IconPlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(20.dp)
            .height(20.dp)
            .shimmerBackground(CircleShape)
    )
}