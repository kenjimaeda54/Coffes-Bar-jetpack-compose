package com.example.coffesbarcompose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithIcon(icon: ImageVector) {

    FilledIconButton(modifier = Modifier
        .width(30.dp)
        .height(40.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(5.dp),
        onClick = { /*TODO*/ }) {
        Image(imageVector = icon, contentDescription = "Image button")

    }

}

@Composable
@Preview(showBackground = true, heightDp = 200, widthDp = 200)
fun ButtonWithIconPreview() {
    ButtonWithIcon(icon = Icons.Default.Add)

}


