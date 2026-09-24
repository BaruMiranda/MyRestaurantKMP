package com.barcode.myrestaurant

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.barcode.myrestaurant.screen.SplashScreen
import com.barcode.myrestaurant.ui.theme.RestaurantTheme

@Composable
fun App() {
    RestaurantTheme {
        Navigator(screen = SplashScreen())
    }
}
