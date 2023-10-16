package com.example.coffesbarcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.coffesbarcompose.screen.MainScreen
import com.example.coffesbarcompose.ui.theme.CoffesBarComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoffesBarComposeTheme {
                // A surface container using the 'background' color from the theme
                   MainScreen()
            }
        }

    }
}

