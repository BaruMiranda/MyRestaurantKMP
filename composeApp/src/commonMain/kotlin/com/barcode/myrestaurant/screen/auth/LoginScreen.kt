package com.barcode.myrestaurant.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.barcode.myrestaurant.ui.components.RestOutlinedButton
import com.barcode.myrestaurant.ui.components.RestPasswordField
import com.barcode.myrestaurant.ui.components.RestTextField
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestaurantFonts
import com.barcode.myrestaurant.ui.theme.RestBody
import com.barcode.myrestaurant.ui.theme.RestCaption
import com.barcode.myrestaurant.ui.theme.RestHeadline
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
        val viewModel = viewModel<LoginViewModel>(
            key = "LoginViewModel"
        ) {
            LoginViewModel(AppContainer.loginUseCase, AppContainer.userPreferences)
        }
        val uiState by viewModel.uiState.collectAsState()

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isVip by remember { mutableStateOf(false) }
        val snackbarHostState = remember { SnackbarHostState() }

        // Navegar al éxito
        LaunchedEffect(uiState.isSuccess) {
            if (uiState.isSuccess) navigator.push(MainScreen())
        }

        // Mostrar error en snackbar
        LaunchedEffect(uiState.errorMessage) {
            uiState.errorMessage?.let {
                snackbarHostState.showSnackbar(it)
                viewModel.clearError()
            }
        }

        androidx.compose.material3.Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = RestaurantColors.Error,
                        contentColor = RestaurantColors.OnError,
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.weight(1f))

                RestText(text = "🍽️", fontSize = 56.sp)
                Spacer(Modifier.height(12.dp))
                RestHeadline(
                    text = "Bienvenido",
                    fontFamily = RestaurantFonts.Serif,
                    color = RestaurantColors.Primary,
                )
                Spacer(Modifier.height(6.dp))
                RestBody(
                    text = "Inicia sesión para continuar",
                    color = RestaurantColors.OnSurfaceVariant,
                )

                Spacer(Modifier.weight(1f))

                RestTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Correo electrónico",
                    placeholder = "tu@correo.com",
                    leadingIcon = Icons.Filled.Email,
                    keyboardType = KeyboardType.Email,
                )

                Spacer(Modifier.height(16.dp))

                RestPasswordField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = isVip,
                        onCheckedChange = { isVip = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = RestaurantColors.Primary,
                            uncheckedColor = RestaurantColors.Border,
                            checkmarkColor = RestaurantColors.OnPrimary,
                        ),
                    )
                    RestText(
                        text = "Soy cliente VIP",
                        color = RestaurantColors.OnSurface,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer(Modifier.height(24.dp))

                RestFilledButton(
                    text = "Iniciar sesión",
                    onClick = { viewModel.login(email, password) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.isNotEmpty() && password.isNotEmpty(),
                    isLoading = uiState.isLoading,
                )

                Spacer(Modifier.height(12.dp))

                RestOutlinedButton(
                    text = "Crear cuenta",
                    onClick = { navigator.push(RegisterScreen()) },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(8.dp))

                RestCaption(
                    text = "Al continuar aceptas nuestros Términos y Condiciones",
                    color = RestaurantColors.Placeholder,
                )

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

