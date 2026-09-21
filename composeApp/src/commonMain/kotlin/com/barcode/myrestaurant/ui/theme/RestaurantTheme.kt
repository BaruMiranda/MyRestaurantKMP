package com.barcode.myrestaurant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val RestaurantColorScheme = lightColorScheme(
    primary            = RestaurantColors.Primary,
    onPrimary          = RestaurantColors.OnPrimary,
    primaryContainer   = RestaurantColors.PrimaryContainer,
    secondary          = RestaurantColors.Secondary,
    onSecondary        = RestaurantColors.OnSecondary,
    secondaryContainer = RestaurantColors.SecondaryContainer,
    tertiary           = RestaurantColors.Tertiary,
    onTertiary         = RestaurantColors.OnTertiary,
    background         = RestaurantColors.Background,
    onBackground       = RestaurantColors.OnBackground,
    surface            = RestaurantColors.Surface,
    onSurface          = RestaurantColors.OnSurface,
    surfaceVariant     = RestaurantColors.SurfaceVariant,
    onSurfaceVariant   = RestaurantColors.OnSurfaceVariant,
    error              = RestaurantColors.Error,
    onError            = RestaurantColors.OnError,
)

@Composable
fun RestaurantTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = RestaurantColorScheme,
        content = content,
    )
}