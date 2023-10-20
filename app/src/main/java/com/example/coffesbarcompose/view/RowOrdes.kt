package com.example.coffesbarcompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.mocks.ordersByUserMock
import com.example.coffesbarcompose.models.Orders
import com.example.coffesbarcompose.ui.theme.fontsInter

@Composable
fun RowOrders(order: Orders) {
    val widthBody = LocalConfiguration.current.screenWidthDp * 0.3

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(10.dp)
    ) {

        Row(
            modifier = Modifier.padding(all = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = ImageRequest.Builder(LocalContext.current).data(order.urlImage).build(),
                contentDescription = "Image Card Row",
                contentScale = ContentScale.FillHeight
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(widthBody.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = order.title, style = TextStyle(
                        fontFamily = fontsInter,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = order.price,
                    style = TextStyle(
                        fontFamily = fontsInter,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 13.sp
                    ),
                )
            }

            Row(
                modifier = Modifier
                    .height(25.dp)
                    .width(80.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonWithIcon(
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp),
                    icon = painterResource(id = R.drawable.pluss),
                    sizeIcon = 15,
                    action = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                )
                Text(
                    text = "1",
                    style = TextStyle(
                        fontFamily = fontsInter,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 15.sp
                    ),
                )
                ButtonWithIcon(
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp),
                    icon = painterResource(id = R.drawable.minus),
                    sizeIcon = 15,
                    action = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                )
            }

        }
    }
}


@Composable
@Preview
fun RowOrdersPreview() {
    RowOrders(order = ordersByUserMock[0])
}