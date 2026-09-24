package com.barcode.myrestaurant.screen.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.barcode.myrestaurant.di.AppContainer
import com.barcode.myrestaurant.screen.auth.LoginScreen
import com.barcode.myrestaurant.ui.components.RestFilledButton
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestText
import kotlinx.coroutines.launch

class OnboardingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator   = LocalNavigator.currentOrThrow
        val scope       = rememberCoroutineScope()
        val pagerState  = rememberPagerState(pageCount = { 3 })
        var selectedType by remember { mutableStateOf<String?>(null) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(RestaurantColors.AuthBackground),
        ) {
            // Decorative circles (background)
            Box(
                Modifier
                    .size(200.dp)
                    .offset(x = (-60).dp, y = (-60).dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.2f))
            )
            Box(
                Modifier
                    .size(140.dp)
                    .offset(x = 300.dp, y = (-30).dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.15f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
            ) {
                // Pager ocupa la mayor parte de la pantalla
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) { page ->
                    when (page) {
                        0 -> InfoPage(
                            icon = Icons.Filled.ShoppingBag,
                            title = "Productos Frescos",
                            description = "Pide productos frescos desde nuestro marketplace, entregados directamente en tu puerta.",
                        )
                        1 -> InfoPage(
                            icon = Icons.Filled.Fastfood,
                            title = "Come aquí o para llevar",
                            description = "Explora nuestro menú y disfruta de deliciosas comidas cuando y donde quieras.",
                        )
                        2 -> TypeSelectionPage(
                            selectedType = selectedType,
                            onTypeSelected = { selectedType = it },
                        )
                    }
                }

                // Dots + botón
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Indicadores de página
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        repeat(3) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .height(8.dp)
                                    .width(if (isSelected) 28.dp else 8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        if (isSelected) RestaurantColors.AuthAccent
                                        else RestaurantColors.AuthAccent.copy(alpha = 0.35f)
                                    ),
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    if (pagerState.currentPage < 2) {
                        RestFilledButton(
                            text = "Siguiente",
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(50.dp),
                            containerColor = RestaurantColors.AuthAccent,
                            contentColor = RestaurantColors.OnSecondary,
                        )
                    } else {
                        RestFilledButton(
                            text = "Comenzar",
                            onClick = {
                                AppContainer.userPreferences.setOnboardingDone(selectedType ?: "persona")
                                navigator.replace(LoginScreen())
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = selectedType != null,
                            shape = RoundedCornerShape(50.dp),
                            containerColor = RestaurantColors.AuthAccent,
                            contentColor = RestaurantColors.OnSecondary,
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun InfoPage(
    icon: ImageVector,
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Círculo con ícono
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(RestaurantColors.AuthAccent),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(72.dp),
            )
        }

        Spacer(Modifier.height(40.dp))

        RestText(
            text = title,
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(16.dp))

        RestText(
            text = description,
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
        )
    }
}

@Composable
private fun TypeSelectionPage(
    selectedType: String?,
    onTypeSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        RestText(
            text = "¿Cómo usarás la app?",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(8.dp))

        RestText(
            text = "Selecciona tu tipo de cuenta para personalizar tu experiencia",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
        )

        Spacer(Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TypeCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Person,
                label = "Persona",
                description = "Cliente individual",
                isSelected = selectedType == "persona",
                onClick = { onTypeSelected("persona") },
            )
            TypeCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Business,
                label = "Empresa",
                description = "Cuenta empresarial",
                isSelected = selectedType == "empresa",
                onClick = { onTypeSelected("empresa") },
            )
        }
    }
}

@Composable
private fun TypeCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val borderColor by animateColorAsState(
        if (isSelected) RestaurantColors.AuthAccent else Color.White.copy(alpha = 0.2f),
    )
    val bgColor by animateColorAsState(
        if (isSelected) RestaurantColors.AuthAccent.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.05f),
    )

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp),
            )
            .clickable { onClick() },
        color = bgColor,
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) RestaurantColors.AuthAccent
                        else Color.White.copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) RestaurantColors.OnSecondary else Color.White,
                    modifier = Modifier.size(28.dp),
                )
            }

            Spacer(Modifier.height(12.dp))

            RestText(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(4.dp))

            RestText(
                text = description,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}
