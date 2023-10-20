package com.example.coffesbarcompose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffesbarcompose.R

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    icon: Painter,
    sizeIcon: Int = 17,
    action: () -> Unit,
    colors: IconButtonColors,
    colorFilter: ColorFilter
) {

    FilledIconButton(
        modifier = modifier
            .width(30.dp)
            .height(40.dp),
        colors = colors,
        shape = RoundedCornerShape(5.dp),
        onClick = action
    ) {
        Image(
            modifier = Modifier.size(sizeIcon.dp),
            painter = icon,
            contentDescription = "Icon Button",
            colorFilter = colorFilter
        )

    }

}


