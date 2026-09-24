package com.barcode.myrestaurant.screen

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.barcode.myrestaurant.di.AppContainer
import com.barcode.myrestaurant.screen.auth.LoginScreen
import com.barcode.myrestaurant.screen.onboarding.OnboardingScreen
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestText
import kotlinx.coroutines.delay

class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var animStarted by remember { mutableStateOf(false) }

        val logoScale by animateFloatAsState(
            targetValue = if (animStarted) 1f else 0.5f,
            animationSpec = tween(durationMillis = 700, easing = OvershootEasing),
        )
        val logoAlpha by animateFloatAsState(
            targetValue = if (animStarted) 1f else 0f,
            animationSpec = tween(durationMillis = 500),
        )
        val textAlpha by animateFloatAsState(
            targetValue = if (animStarted) 1f else 0f,
            animationSpec = tween(durationMillis = 500, delayMillis = 300),
        )

        LaunchedEffect(Unit) {
            animStarted = true
            delay(2200)
            val prefs = AppContainer.userPreferences
            val next = when {
                !prefs.isOnboardingDone() -> OnboardingScreen()
                prefs.isLoggedIn()        -> MainScreen()
                else                      -> LoginScreen()
            }
            navigator.replace(next)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(RestaurantColors.AuthBackground),
            contentAlignment = Alignment.Center,
        ) {
            // Grandes círculos decorativos de fondo
            Box(
                Modifier
                    .size(420.dp)
                    .offset(x = (-120).dp, y = (-180).dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.07f))
            )
            Box(
                Modifier
                    .size(320.dp)
                    .offset(x = 160.dp, y = 220.dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.07f))
            )
            // Círculos pequeños decorativos
            Box(
                Modifier
                    .size(28.dp)
                    .offset(x = (-130).dp, y = 160.dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.5f))
            )
            Box(
                Modifier
                    .size(16.dp)
                    .offset(x = 150.dp, y = (-200).dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.4f))
            )
            Box(
                Modifier
                    .size(12.dp)
                    .offset(x = (-160).dp, y = (-140).dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.35f))
            )

            // Contenido central
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                // Logo con animación
                Box(
                    modifier = Modifier
                        .scale(logoScale)
                        .graphicsLayer(alpha = logoAlpha),
                ) {
                    // Sombra / glow exterior
                    Box(
                        modifier = Modifier
                            .size(186.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.AuthAccent.copy(alpha = 0.25f))
                            .align(Alignment.Center),
                    )
                    // Círculo principal
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.AuthAccent)
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Fastfood,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(72.dp),
                        )
                    }
                }

                Spacer(Modifier.height(28.dp))

                // Nombre de la app
                Column(
                    modifier = Modifier.graphicsLayer(alpha = textAlpha),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    RestText(
                        text = "MyRestaurant",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(6.dp))
                    RestText(
                        text = "Comida fresca al alcance de tu mano",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            // Indicador de carga en la parte baja
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .size(24.dp)
                    .graphicsLayer(alpha = textAlpha),
                color = RestaurantColors.AuthAccent.copy(alpha = 0.7f),
                strokeWidth = 2.dp,
            )
        }
    }
}

private val OvershootEasing = Easing { t ->
    val c1 = 1.70158f
    val c3 = c1 + 1f
    (c3 * t * t * t - c1 * t * t).toFloat()
        .let { if (t < 1f) it else 1f + (t - 1f).let { r -> c3 * r * r * r - c1 * r * r } }
}
