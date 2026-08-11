package com.barcode.myrestaurant

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.barcode.myrestaurant.screen.MainScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(screen = MainScreen())
    }
}