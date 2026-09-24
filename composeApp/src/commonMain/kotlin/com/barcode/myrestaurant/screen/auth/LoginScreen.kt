package com.barcode.myrestaurant.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.barcode.myrestaurant.di.AppContainer
import com.barcode.myrestaurant.presentation.auth.LoginViewModel
import com.barcode.myrestaurant.screen.MainScreen
import com.barcode.myrestaurant.ui.components.RestFilledButton
import com.barcode.myrestaurant.ui.components.RestPasswordField
import com.barcode.myrestaurant.ui.components.RestTextField
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestText

class LoginScreen : Screen {

    companion object {
        const val KEY_EMAIL         = "EMAIL"
        const val KEY_PASSWORD      = "PASSWORD"
        const val KEY_VIP           = "VIP"
        const val KEY_NAME          = "NAME"
        const val KEY_PHONE         = "PHONE"
        const val KEY_CUSTOMER_TYPE = "CUSTOMER_TYPE"
    }

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = viewModel<LoginViewModel>(key = "LoginViewModel") {
            LoginViewModel(AppContainer.loginUseCase, AppContainer.userPreferences)
        }
        val uiState by viewModel.uiState.collectAsState()

        var isOwnerMode  by remember { mutableStateOf(true) }
        var email        by remember { mutableStateOf("") }
        var password     by remember { mutableStateOf("") }
        var employeeCode by remember { mutableStateOf("") }
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(uiState.isSuccess) {
            if (uiState.isSuccess) navigator.push(MainScreen())
        }
        LaunchedEffect(uiState.errorMessage) {
            uiState.errorMessage?.let {
                snackbarHostState.showSnackbar(it)
                viewModel.clearError()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(RestaurantColors.AuthBackground)
                .imePadding()
        ) {
            // Decorative dots
            Box(
                Modifier
                    .size(22.dp)
                    .offset(x = 20.dp, y = 140.dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent)
            )
            Box(
                Modifier
                    .size(13.dp)
                    .offset(x = 52.dp, y = 210.dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.6f))
            )
            Box(
                Modifier
                    .size(18.dp)
                    .offset(x = 340.dp, y = 100.dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.5f))
            )

            Column(Modifier.fillMaxSize()) {
                // Top green section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.42f)
                        .statusBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.AuthAccent),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Fastfood,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(58.dp),
                        )
                    }
                    Spacer(Modifier.height(18.dp))
                    RestText(
                        text = "¡Bienvenido de vuelta!",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // White card
                Surface(
                    modifier = Modifier.fillMaxWidth().weight(0.58f),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    color = Color.White,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 28.dp)
                            .navigationBarsPadding(),
                    ) {
                        Spacer(Modifier.height(28.dp))

                        // Toggle Dueño / Empleado
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(50.dp),
                            color = RestaurantColors.Surface,
                        ) {
                            Row(Modifier.padding(4.dp)) {
                                listOf(true to "Dueño", false to "Empleado").forEach { (isOwner, label) ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(50.dp))
                                            .background(
                                                if (isOwnerMode == isOwner) RestaurantColors.Primary
                                                else Color.Transparent
                                            )
                                            .clickable { isOwnerMode = isOwner }
                                            .padding(vertical = 10.dp),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        RestText(
                                            text = label,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = if (isOwnerMode == isOwner) Color.White
                                                    else RestaurantColors.OnSurfaceVariant,
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(28.dp))

                        if (isOwnerMode) {
                            RestText(
                                text = "Correo electrónico",
                                color = RestaurantColors.OnSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                            )
                            Spacer(Modifier.height(6.dp))
                            RestTextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = "johnwilliams@gmail.com",
                                keyboardType = KeyboardType.Email,
                            )

                            Spacer(Modifier.height(20.dp))

                            RestText(
                                text = "Contraseña",
                                color = RestaurantColors.OnSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                            )
                            Spacer(Modifier.height(6.dp))
                            RestPasswordField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = "",
                                placeholder = "••••••••",
                            )

                            Spacer(Modifier.height(36.dp))

                            RestFilledButton(
                                text = "INICIAR SESIÓN",
                                onClick = { viewModel.login(email, password) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = email.isNotEmpty() && password.isNotEmpty(),
                                isLoading = uiState.isLoading,
                                shape = RoundedCornerShape(50.dp),
                                containerColor = RestaurantColors.Primary,
                            )
                        } else {
                            RestText(
                                text = "Ingresa tu código de empleado",
                                color = RestaurantColors.OnSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                            )
                            Spacer(Modifier.height(6.dp))
                            RestTextField(
                                value = employeeCode,
                                onValueChange = { employeeCode = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = "EMP-0000",
                            )

                            Spacer(Modifier.height(36.dp))

                            RestFilledButton(
                                text = "INICIAR SESIÓN",
                                onClick = { /* TODO: employee login */ },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = employeeCode.isNotEmpty(),
                                shape = RoundedCornerShape(50.dp),
                                containerColor = RestaurantColors.Primary,
                            )
                        }

                        Spacer(Modifier.height(32.dp))
                    }
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = RestaurantColors.Error,
                    contentColor = RestaurantColors.OnError,
                )
            }
        }
    }
}
