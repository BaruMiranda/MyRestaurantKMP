package com.barcode.myrestaurant.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barcode.myrestaurant.di.AppContainer
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

object ProfileTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember { TabOptions(index = 2u, title = "Perfil", icon = icon) }
        }

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = viewModel<ProfileViewModel>(key = "ProfileViewModel") {
            ProfileViewModel(AppContainer.userPreferences)
        }
        val state by viewModel.uiState.collectAsState()

        LaunchedEffect(state.isLoggedOut) {
            if (state.isLoggedOut) (navigator.parent ?: navigator).replaceAll(LoginScreen())
        }

        val initials = state.name.trim()
            .split(" ").filter { it.isNotEmpty() }.take(2)
            .joinToString("") { it.first().uppercaseChar().toString() }
            .ifEmpty { state.email.firstOrNull()?.uppercaseChar()?.toString() ?: "?" }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Cabecera estilo auth
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RestaurantColors.AuthBackground),
            ) {
                Box(
                    Modifier.size(110.dp).offset(x = 300.dp, y = (-35).dp)
                        .clip(CircleShape)
                        .background(RestaurantColors.AuthAccent.copy(alpha = 0.75f))
                )
                Box(
                    Modifier.size(18.dp).offset(x = 270.dp, y = 65.dp)
                        .clip(CircleShape)
                        .background(RestaurantColors.AuthAccent.copy(alpha = 0.45f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.AuthAccent.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        RestText(
                            text = initials,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = RestaurantColors.AuthAccent,
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    RestHeadline(
                        text = state.name.ifEmpty { "Sin nombre" },
                        fontFamily = RestaurantFonts.Serif,
                        color = Color.White,
                    )
                    Spacer(Modifier.height(4.dp))
                    RestBody(text = state.email, color = Color.White.copy(alpha = 0.7f))

                    if (state.isVip) {
                        Spacer(Modifier.height(8.dp))
                        Surface(
                            color = RestaurantColors.AuthAccent,
                            shape = RoundedCornerShape(20.dp),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                RestText(text = "⭐", fontSize = 12.sp)
                                Spacer(Modifier.size(4.dp))
                                RestCaption(text = "Cliente VIP", color = RestaurantColors.OnSecondary)
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
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

                RestTextField(
                    value = state.name,
                    onValueChange = viewModel::onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = "Nombre completo",
                    placeholder = "Tu nombre",
                    leadingIcon = Icons.Filled.Person,
                    errorMessage = state.nameError,
                    imeAction = ImeAction.Next,
                )
                Spacer(Modifier.height(16.dp))

                RestTextField(
                    value = state.email,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = "Correo electrónico",
                    leadingIcon = Icons.Filled.Email,
                    readOnly = true,
                    supportingText = "El correo no se puede modificar",
                )
                Spacer(Modifier.height(16.dp))

                RestTextField(
                    value = state.phone,
                    onValueChange = viewModel::onPhoneChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = "Teléfono",
                    placeholder = "+52 55 1234 5678",
                    leadingIcon = Icons.Filled.Phone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                )
                Spacer(Modifier.height(16.dp))

                RestDropdown(
                    selectedItem = state.customerType,
                    items = viewModel.customerTypes,
                    onItemSelected = viewModel::onCustomerTypeChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = "Tipo de cliente",
                    leadingIcon = Icons.Filled.Star,
                )
                Spacer(Modifier.height(24.dp))

                RestFilledButton(
                    text = if (state.saved) "✓ Cambios guardados" else "Guardar cambios",
                    onClick = viewModel::saveChanges,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = if (state.saved) RestaurantColors.Success else RestaurantColors.Primary,
                )
                Spacer(Modifier.height(16.dp))

                HorizontalDivider(color = RestaurantColors.Divider)
                Spacer(Modifier.height(16.dp))

                RestOutlinedButton(
                    text = "Cerrar sesión",
                    onClick = viewModel::logout,
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = RestaurantColors.Error,
                    contentColor = RestaurantColors.Error,
                )
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
