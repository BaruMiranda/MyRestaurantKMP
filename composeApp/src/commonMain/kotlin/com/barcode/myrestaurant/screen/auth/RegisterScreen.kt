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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.barcode.myrestaurant.screen.MainScreen
import com.barcode.myrestaurant.ui.components.RestFilledButton
import com.barcode.myrestaurant.ui.components.RestPasswordField
import com.barcode.myrestaurant.ui.components.RestTextField
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestText
import com.russhwolf.settings.Settings

class RegisterScreen : Screen {

    private val settings: Settings = Settings()

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var name            by remember { mutableStateOf("") }
        var phone           by remember { mutableStateOf("") }
        var email           by remember { mutableStateOf("") }
        var password        by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var isLoading       by remember { mutableStateOf(false) }

        var nameError            by remember { mutableStateOf<String?>(null) }
        var emailError           by remember { mutableStateOf<String?>(null) }
        var passwordError        by remember { mutableStateOf<String?>(null) }
        var confirmPasswordError by remember { mutableStateOf<String?>(null) }

        val canRegister = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(RestaurantColors.AuthBackground)
                .imePadding()
        ) {
            // Decorative circles
            Box(
                Modifier
                    .size(180.dp)
                    .offset(x = 260.dp, y = (-60).dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent)
            )
            Box(
                Modifier
                    .size(24.dp)
                    .offset(x = 18.dp, y = 60.dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent)
            )
            Box(
                Modifier
                    .size(14.dp)
                    .offset(x = 50.dp, y = 110.dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.6f))
            )

            Column(Modifier.fillMaxSize()) {
                // Small dark area at top
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .weight(0.14f)
                        .statusBarsPadding()
                )

                // White card
                Surface(
                    modifier = Modifier.fillMaxWidth().weight(0.86f),
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
                        Spacer(Modifier.height(32.dp))

                        RestText(
                            text = "Crear cuenta",
                            color = RestaurantColors.OnSurface,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(Modifier.height(4.dp))
                        RestText(
                            text = "Completa tus datos para comenzar",
                            color = RestaurantColors.OnSurfaceVariant,
                            fontSize = 13.sp,
                        )

                        Spacer(Modifier.height(28.dp))

                        FieldLabel("Nombre")
                        Spacer(Modifier.height(6.dp))
                        RestTextField(
                            value = name,
                            onValueChange = { name = it; nameError = null },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "Juan Pérez",
                            leadingIcon = Icons.Filled.Person,
                            errorMessage = nameError,
                            imeAction = ImeAction.Next,
                        )

                        Spacer(Modifier.height(18.dp))

                        FieldLabel("Correo electrónico")
                        Spacer(Modifier.height(6.dp))
                        RestTextField(
                            value = email,
                            onValueChange = { email = it; emailError = null },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "johnwilliams@gmail.com",
                            leadingIcon = Icons.Filled.Email,
                            errorMessage = emailError,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        )

                        Spacer(Modifier.height(18.dp))

                        FieldLabel("Teléfono")
                        Spacer(Modifier.height(6.dp))
                        RestTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "+1 234 567 8900",
                            leadingIcon = Icons.Filled.Phone,
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next,
                        )

                        Spacer(Modifier.height(18.dp))

                        FieldLabel("Contraseña")
                        Spacer(Modifier.height(6.dp))
                        RestPasswordField(
                            value = password,
                            onValueChange = { password = it; passwordError = null },
                            modifier = Modifier.fillMaxWidth(),
                            label = "",
                            placeholder = "••••••••",
                            errorMessage = passwordError,
                            imeAction = ImeAction.Next,
                        )

                        Spacer(Modifier.height(18.dp))

                        FieldLabel("Confirmar contraseña")
                        Spacer(Modifier.height(6.dp))
                        RestPasswordField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it; confirmPasswordError = null },
                            modifier = Modifier.fillMaxWidth(),
                            label = "",
                            placeholder = "••••••••",
                            errorMessage = confirmPasswordError,
                            imeAction = ImeAction.Done,
                        )

                        Spacer(Modifier.height(36.dp))

                        RestFilledButton(
                            text = "CREAR CUENTA",
                            onClick = {
                                var valid = true
                                if (name.isBlank()) { nameError = "Obligatorio"; valid = false }
                                if (email.isBlank()) { emailError = "Obligatorio"; valid = false }
                                if (password.length < 6) { passwordError = "Mínimo 6 caracteres"; valid = false }
                                if (confirmPassword != password) { confirmPasswordError = "Las contraseñas no coinciden"; valid = false }
                                if (valid) {
                                    isLoading = true
                                    settings.putString(LoginScreen.KEY_EMAIL, email)
                                    settings.putString(LoginScreen.KEY_PASSWORD, password)
                                    settings.putString(LoginScreen.KEY_NAME, name)
                                    settings.putString(LoginScreen.KEY_PHONE, phone)
                                    navigator.replaceAll(MainScreen())
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = canRegister,
                            isLoading = isLoading,
                            shape = RoundedCornerShape(50.dp),
                            containerColor = RestaurantColors.Primary,
                        )

                        Spacer(Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RestText(
                                text = "¿Ya tienes cuenta? ",
                                color = RestaurantColors.OnSurfaceVariant,
                                fontSize = 14.sp,
                            )
                            RestText(
                                text = "Inicia sesión",
                                color = RestaurantColors.Primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.clickable { navigator.pop() },
                            )
                        }

                        Spacer(Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    RestText(
        text = text,
        color = RestaurantColors.OnSurface,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
    )
}
