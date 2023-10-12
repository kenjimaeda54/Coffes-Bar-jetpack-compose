package com.example.coffesbarcompose.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffesbarcompose.mocks.coffeesMock
import com.example.coffesbarcompose.models.CoffeesModel
import com.example.coffesbarcompose.ui.theme.fontsInter

@Composable
fun RowCoffee(modifier: Modifier = Modifier,coffee: CoffeesModel) {

    Surface(
        modifier = modifier
            .padding(all = 10.dp)
            .height(220.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(modifier = Modifier.padding(all = 5.dp)) {
            AsyncImage(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(5.dp)
                    ),
                model = ImageRequest.Builder(LocalContext.current).data(coffee.urlPhoto)
                    .build(),
                contentDescription = "Image photo",
                contentScale = ContentScale.FillHeight
            )
            Text(
                coffee.name,
                modifier = Modifier
                    .height(60.dp)
                    .padding(vertical = 5.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontFamily = fontsInter,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary

            )
            Surface(
                modifier = Modifier.height(40.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(7.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = coffee.price,
                        textAlign = TextAlign.Center,
                        fontFamily = fontsInter,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White)
                    
                    ButtonWithIcon(icon = Icons.Default.Add)
                }
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun RowCoffePreview() {
    RowCoffee(coffee = coffeesMock[0])
}