package com.example.coffesbarcompose.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffesbarcompose.ui.theme.fontsInter

@Composable
fun ButtonCommon(
    modifier: Modifier = Modifier,
    title: String,
    action: () -> Unit,
    enable: Boolean = true,
    feedbackLoading: Boolean = false
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .height(33.dp),
        shape = RoundedCornerShape(7.dp),
        contentPadding = PaddingValues(all = 5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(0.6f)
        ),
        enabled = enable,
        onClick = { action.invoke() }) {
        if (feedbackLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(15.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 1.dp
                )

        } else {
            Text(
                text = title,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    fontFamily = fontsInter,
                    fontSize = 18.sp
                ),
            )
        }
    }
}


