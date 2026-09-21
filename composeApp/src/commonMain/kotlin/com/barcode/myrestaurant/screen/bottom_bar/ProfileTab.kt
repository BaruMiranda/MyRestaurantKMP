package com.barcode.myrestaurant.screen.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barcode.myrestaurant.screen.auth.LoginScreen
import com.barcode.myrestaurant.ui.components.RestDropdown
import com.barcode.myrestaurant.ui.components.RestFilledButton
import com.barcode.myrestaurant.ui.components.RestOutlinedButton
import com.barcode.myrestaurant.ui.components.RestTextField
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestaurantFonts
import com.barcode.myrestaurant.ui.theme.RestBody
import com.barcode.myrestaurant.ui.theme.RestCaption
import com.barcode.myrestaurant.ui.theme.RestHeadline
import com.barcode.myrestaurant.ui.theme.RestText
import com.barcode.myrestaurant.ui.theme.RestTitle
import com.russhwolf.settings.Settings

object ProfileTab : Tab {

    private val settings: Settings = Settings()

    private val customerTypes = listOf("Cliente Regular", "Cliente VIP", "Cliente Empresarial")

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember {
                TabOptions(index = 3u, title = "Perfil", icon = icon)
            }
        }

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val savedEmail = settings.getString(LoginScreen.KEY_EMAIL, "")
        val savedName  = settings.getString(LoginScreen.KEY_NAME, "")
        val savedPhone = settings.getString(LoginScreen.KEY_PHONE, "")
        val savedType  = settings.getString(LoginScreen.KEY_CUSTOMER_TYPE, customerTypes[0])

        var name by remember { mutableStateOf(savedName) }
        var phone by remember { mutableStateOf(savedPhone) }
        var customerType by remember { mutableStateOf(savedType) }
        var saved by remember { mutableStateOf(false) }

        var nameError by remember { mutableStateOf<String?>(null) }

        val initials = name.trim()
            .split(" ")
            .filter { it.isNotEmpty() }
            .take(2)
            .joinToString("") { it.first().uppercaseChar().toString() }
            .ifEmpty { savedEmail.firstOrNull()?.uppercaseChar()?.toString() ?: "?" }

        val isVip = customerType == "Cliente VIP"

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Cabecera con fondo Primary
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RestaurantColors.Primary)
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Avatar con iniciales
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.OnPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        RestText(
                            text = initials,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = RestaurantColors.OnPrimary,
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    RestHeadline(
                        text = name.ifEmpty { "Sin nombre" },
                        fontFamily = RestaurantFonts.Serif,
                        color = RestaurantColors.OnPrimary,
                    )

                    Spacer(Modifier.height(4.dp))

                    RestBody(
                        text = savedEmail,
                        color = RestaurantColors.OnPrimary.copy(alpha = 0.8f),
                    )

                    if (isVip) {
                        Spacer(Modifier.height(8.dp))
                        Surface(
                            color = RestaurantColors.Secondary,
                            shape = RoundedCornerShape(20.dp),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                RestText(text = "⭐", fontSize = 12.sp)
                                Spacer(Modifier.size(4.dp))
                                RestCaption(
                                    text = "Cliente VIP",
                                    color = RestaurantColors.OnSecondary,
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            ) {
                RestTitle(text = "Datos personales", color = RestaurantColors.OnBackground)

                Spacer(Modifier.height(16.dp))

                // Nombre
                RestTextField(
                    value = name,
                    onValueChange = { name = it; nameError = null; saved = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Nombre completo",
                    placeholder = "Tu nombre",
                    leadingIcon = Icons.Filled.Person,
                    errorMessage = nameError,
                    imeAction = ImeAction.Next,
                )

                Spacer(Modifier.height(16.dp))

                // Email (solo lectura)
                RestTextField(
                    value = savedEmail,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = "Correo electrónico",
                    leadingIcon = Icons.Filled.Email,
                    readOnly = true,
                    supportingText = "El correo no se puede modificar",
                )

                Spacer(Modifier.height(16.dp))

                // Teléfono
                RestTextField(
                    value = phone,
                    onValueChange = { phone = it; saved = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Teléfono",
                    placeholder = "+52 55 1234 5678",
                    leadingIcon = Icons.Filled.Phone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                )

                Spacer(Modifier.height(16.dp))

                // Tipo de cliente
                RestDropdown(
                    selectedItem = customerType,
                    items = customerTypes,
                    onItemSelected = { customerType = it; saved = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Tipo de cliente",
                    leadingIcon = Icons.Filled.Star,
                )

                Spacer(Modifier.height(24.dp))

                // Botón guardar
                RestFilledButton(
                    text = if (saved) "✓ Cambios guardados" else "Guardar cambios",
                    onClick = {
                        if (name.isBlank()) {
                            nameError = "El nombre no puede estar vacío"
                        } else {
                            settings.putString(LoginScreen.KEY_NAME, name)
                            settings.putString(LoginScreen.KEY_PHONE, phone)
                            settings.putString(LoginScreen.KEY_CUSTOMER_TYPE, customerType)
                            settings.putBoolean(LoginScreen.KEY_VIP, isVip)
                            saved = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = if (saved) RestaurantColors.Success else RestaurantColors.Primary,
                )

                Spacer(Modifier.height(16.dp))

                HorizontalDivider(color = RestaurantColors.Divider)

                Spacer(Modifier.height(16.dp))

                // Cerrar sesión
                RestOutlinedButton(
                    text = "Cerrar sesión",
                    onClick = {
                        settings.clear()
                        navigator.replaceAll(LoginScreen())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = RestaurantColors.Error,
                    contentColor = RestaurantColors.Error,
                )

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
