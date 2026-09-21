package com.barcode.myrestaurant.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.barcode.myrestaurant.screen.MainScreen
import com.barcode.myrestaurant.ui.components.RestDropdown
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
import com.russhwolf.settings.Settings

class RegisterScreen : Screen {

    private val settings: Settings = Settings()

    private val customerTypes = listOf("Cliente Regular", "Cliente VIP", "Cliente Empresarial")

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var customerType by remember { mutableStateOf<String?>(null) }
        var acceptTerms by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }

        var nameError by remember { mutableStateOf<String?>(null) }
        var phoneError by remember { mutableStateOf<String?>(null) }
        var emailError by remember { mutableStateOf<String?>(null) }
        var passwordError by remember { mutableStateOf<String?>(null) }
        var confirmPasswordError by remember { mutableStateOf<String?>(null) }
        var customerTypeError by remember { mutableStateOf<String?>(null) }

        val canRegister = name.isNotEmpty() && email.isNotEmpty()
                && password.isNotEmpty() && acceptTerms

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Top bar con botón atrás
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = RestaurantColors.OnBackground,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Header
                RestText(text = "👤", fontSize = 48.sp)
                Spacer(Modifier.height(12.dp))
                RestHeadline(
                    text = "Crear cuenta",
                    fontFamily = RestaurantFonts.Serif,
                    color = RestaurantColors.Primary,
                )
                Spacer(Modifier.height(6.dp))
                RestBody(
                    text = "Únete y disfruta de nuestra experiencia",
                    color = RestaurantColors.OnSurfaceVariant,
                )

                Spacer(Modifier.height(28.dp))

                // Nombre completo
                RestTextField(
                    value = name,
                    onValueChange = { name = it; nameError = null },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Nombre completo",
                    placeholder = "Juan Pérez",
                    leadingIcon = Icons.Filled.Person,
                    errorMessage = nameError,
                    imeAction = ImeAction.Next,
                )

                Spacer(Modifier.height(16.dp))

                // Teléfono
                RestTextField(
                    value = phone,
                    onValueChange = { phone = it; phoneError = null },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Teléfono",
                    placeholder = "+52 55 1234 5678",
                    leadingIcon = Icons.Filled.Phone,
                    errorMessage = phoneError,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                )

                Spacer(Modifier.height(16.dp))

                // Correo
                RestTextField(
                    value = email,
                    onValueChange = { email = it; emailError = null },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Correo electrónico",
                    placeholder = "tu@correo.com",
                    leadingIcon = Icons.Filled.Email,
                    errorMessage = emailError,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )

                Spacer(Modifier.height(16.dp))

                // Contraseña
                RestPasswordField(
                    value = password,
                    onValueChange = { password = it; passwordError = null },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Contraseña",
                    placeholder = "Mínimo 6 caracteres",
                    errorMessage = passwordError,
                    imeAction = ImeAction.Next,
                )

                Spacer(Modifier.height(16.dp))

                // Confirmar contraseña
                RestPasswordField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; confirmPasswordError = null },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Confirmar contraseña",
                    placeholder = "Repite tu contraseña",
                    errorMessage = confirmPasswordError,
                    imeAction = ImeAction.Done,
                )

                Spacer(Modifier.height(16.dp))

                // Tipo de cliente (Dropdown)
                RestDropdown(
                    selectedItem = customerType,
                    items = customerTypes,
                    onItemSelected = { customerType = it; customerTypeError = null },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Tipo de cliente",
                    placeholder = "Selecciona tu tipo de cuenta",
                    leadingIcon = Icons.Filled.Star,
                    errorMessage = customerTypeError,
                )

                Spacer(Modifier.height(16.dp))

                // Términos y condiciones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = acceptTerms,
                        onCheckedChange = { acceptTerms = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = RestaurantColors.Primary,
                            uncheckedColor = RestaurantColors.Border,
                            checkmarkColor = RestaurantColors.OnPrimary,
                        ),
                    )
                    RestCaption(
                        text = "Acepto los Términos y Condiciones y la Política de Privacidad",
                        color = RestaurantColors.OnSurfaceVariant,
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Botón registrarse
                RestFilledButton(
                    text = "Registrarme",
                    onClick = {
                        var valid = true
                        if (name.isBlank()) { nameError = "El nombre es obligatorio"; valid = false }
                        if (email.isBlank()) { emailError = "El correo es obligatorio"; valid = false }
                        if (password.length < 6) { passwordError = "Mínimo 6 caracteres"; valid = false }
                        if (confirmPassword != password) { confirmPasswordError = "Las contraseñas no coinciden"; valid = false }
                        if (customerType == null) { customerTypeError = "Selecciona un tipo de cuenta"; valid = false }
                        if (valid) {
                            isLoading = true
                            settings.putString(LoginScreen.KEY_EMAIL, email)
                            settings.putString(LoginScreen.KEY_PASSWORD, password)
                            settings.putString(LoginScreen.KEY_NAME, name)
                            settings.putString(LoginScreen.KEY_PHONE, phone)
                            settings.putString(LoginScreen.KEY_CUSTOMER_TYPE, customerType ?: "Cliente Regular")
                            settings.putBoolean(LoginScreen.KEY_VIP, customerType == "Cliente VIP")
                            navigator.replaceAll(MainScreen())
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canRegister,
                    isLoading = isLoading,
                )

                Spacer(Modifier.height(12.dp))

                // Volver al login
                RestOutlinedButton(
                    text = "Ya tengo cuenta",
                    onClick = { navigator.pop() },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
