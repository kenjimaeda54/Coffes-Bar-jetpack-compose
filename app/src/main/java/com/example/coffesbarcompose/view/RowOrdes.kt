package com.example.coffesbarcompose.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.R
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.models.Orders
import com.example.coffesbarcompose.ui.theme.fontsInter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowOrders(
    modifier: Modifier = Modifier,
    order: Orders,
    actionAdd: () -> Unit,
    actionRemove: () -> Unit,
    actionDeleteOrder: () -> Unit,
) {

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val widthPx = with(density) {
        configuration.screenWidthDp.dp.roundToPx()
    }

    val widthBody = LocalConfiguration.current.screenWidthDp * 0.3
    val swappableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { width.dp.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps

    suspend fun handleSnapTo() {

    }

    //preciso retonar o valor para 0 assim nao corro risco deletar outro da lista que nao e oque desejo
    //launchedEffect permite trabalhar com courtines
    if (swappableState.offset.value.toInt() > (widthPx - 1)) {
        LaunchedEffect(Unit) {
            actionDeleteOrder.invoke()
            swappableState.snapTo(0)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()

            .swipeable(
                state = swappableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal,
                reverseDirection = true,
            )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.zIndex(-1f), contentAlignment = Alignment.CenterStart) {
                Image(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
                )
            }
        }

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(75.dp)
                .offset {
                    IntOffset(
                        swappableState.offset.value
                            .roundToInt()
                            .unaryMinus(), 0
                    )
                },
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 5.dp),
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
                        text = order.title,
                        style = TextStyle(
                            fontFamily = fontsInter,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
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
                        action = { actionAdd.invoke() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    )
                    Text(
                        text = "${order.quantity}",
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
                        action = { actionRemove.invoke() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    )
                }
            }
        }

    }
}


