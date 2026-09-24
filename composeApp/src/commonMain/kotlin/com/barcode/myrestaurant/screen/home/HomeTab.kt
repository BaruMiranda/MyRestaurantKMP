package com.barcode.myrestaurant.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Badge
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barcode.myrestaurant.di.AppContainer
import com.barcode.myrestaurant.ui.components.AppHeader
import com.barcode.myrestaurant.util.formatPrice
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestText

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)
            return remember { TabOptions(index = 0u, title = "Inicio", icon = icon) }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = viewModel<HomeViewModel>(key = "HomeViewModel") {
            HomeViewModel(AppContainer.userPreferences)
        }
        val state by viewModel.uiState.collectAsState()
        val name = viewModel.userName.ifEmpty { "Invitado" }
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        Box(modifier = Modifier.fillMaxSize()) {

            // ── Contenido principal ─────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                AppHeader(
                    title = "¡Hola, $name!",
                    subtitle = "¿Qué te apetece comer hoy?",
                )

                Spacer(Modifier.height(20.dp))

                // ── Menú del día ──────────────────────────────────────────
                SectionTitle(text = "Menú del día", modifier = Modifier.padding(horizontal = 20.dp))

                Spacer(Modifier.height(14.dp))

                SubsectionLabel(text = "Entradas", modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(10.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.entradas) { item ->
                        MenuDayCard(
                            item = item,
                            cartItems = state.cartItems,
                            onAdd = { viewModel.addToCart(item) },
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                SubsectionLabel(text = "Segundo", modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(10.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.segundos) { item ->
                        MenuDayCard(
                            item = item,
                            cartItems = state.cartItems,
                            onAdd = { viewModel.addToCart(item) },
                        )
                    }
                }

                Spacer(Modifier.height(28.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = RestaurantColors.Divider,
                )
                Spacer(Modifier.height(24.dp))

                // ── Platos a la carta ─────────────────────────────────────
                RestText(
                    text = "Platos a la carta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = RestaurantColors.OnBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(16.dp))

                state.alaCartaItems.forEach { item ->
                    AlaCartaCard(
                        item = item,
                        cartItems = state.cartItems,
                        onAdd = { viewModel.addToCart(item) },
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                    Spacer(Modifier.height(12.dp))
                }

                Spacer(Modifier.height(96.dp)) // espacio para el FAB
            }

            // ── FAB ───────────────────────────────────────────────────────
            AnimatedVisibility(
                visible = state.hasItems,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 20.dp),
            ) {
                FloatingActionButton(
                    onClick = viewModel::showBottomSheet,
                    containerColor = RestaurantColors.AuthBackground,
                    contentColor = RestaurantColors.AuthAccent,
                    shape = CircleShape,
                ) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = RestaurantColors.AuthAccent,
                                contentColor = RestaurantColors.OnSecondary,
                            ) {
                                RestText(
                                    text = state.totalItems.toString(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RestaurantColors.OnSecondary,
                                )
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingBag,
                            contentDescription = "Ver pedido",
                            modifier = Modifier.size(26.dp),
                        )
                    }
                }
            }
        }

        // ── Bottom Sheet ──────────────────────────────────────────────────
        if (state.isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = viewModel::hideBottomSheet,
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            ) {
                OrderBottomSheet(
                    state = state,
                    onIncrease = viewModel::increaseQty,
                    onDecrease = viewModel::decreaseQty,
                    onPlaceOrder = viewModel::placeOrder,
                )
            }
        }
    }
}

// ── Composables privados ─────────────────────────────────────────────────────

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    RestText(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = RestaurantColors.OnBackground,
        modifier = modifier,
    )
}

@Composable
private fun SubsectionLabel(text: String, modifier: Modifier = Modifier) {
    RestText(
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = RestaurantColors.OnSurfaceVariant,
        modifier = modifier,
    )
}

@Composable
private fun MenuDayCard(
    item: MenuItem,
    cartItems: List<CartItem>,
    onAdd: () -> Unit,
) {
    val qty = cartItems.find { it.item.id == item.id }?.quantity ?: 0

    Card(
        modifier = Modifier.width(155.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column {
            // Imagen placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(RestaurantColors.AuthBackground),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(RestaurantColors.AuthAccent.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Fastfood,
                        contentDescription = null,
                        tint = RestaurantColors.AuthAccent,
                        modifier = Modifier.size(28.dp),
                    )
                }
                // Badge de cantidad
                if (qty > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.AuthAccent),
                        contentAlignment = Alignment.Center,
                    ) {
                        RestText(text = qty.toString(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RestaurantColors.OnSecondary)
                    }
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                RestText(
                    text = item.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = RestaurantColors.OnSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(4.dp))
                RestText(
                    text = "S/. ${item.price.formatPrice()}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = RestaurantColors.Primary,
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = onAdd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RestaurantColors.Primary,
                        contentColor = Color.White,
                    ),
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    RestText(text = "Agregar", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
private fun AlaCartaCard(
    item: MenuItem,
    cartItems: List<CartItem>,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val qty = cartItems.find { it.item.id == item.id }?.quantity ?: 0

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Info izquierda
            Column(modifier = Modifier.weight(1f)) {
                RestText(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = RestaurantColors.OnSurface,
                )
                Spacer(Modifier.height(4.dp))
                RestText(
                    text = item.description,
                    fontSize = 12.sp,
                    color = RestaurantColors.OnSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 17.sp,
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    RestText(
                        text = "S/. ${item.price.formatPrice()}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = RestaurantColors.Primary,
                    )
                    Button(
                        onClick = onAdd,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        modifier = Modifier.height(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RestaurantColors.Primary,
                            contentColor = Color.White,
                        ),
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        RestText(text = "Agregar", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Medium)
                    }
                    if (qty > 0) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(RestaurantColors.AuthAccent),
                            contentAlignment = Alignment.Center,
                        ) {
                            RestText(text = qty.toString(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RestaurantColors.OnSecondary)
                        }
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            // Imagen placeholder derecha
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(RestaurantColors.AuthBackground),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Fastfood,
                    contentDescription = null,
                    tint = RestaurantColors.AuthAccent,
                    modifier = Modifier.size(36.dp),
                )
            }
        }
    }
}

@Composable
private fun OrderBottomSheet(
    state: HomeUiState,
    onIncrease: (MenuItem) -> Unit,
    onDecrease: (MenuItem) -> Unit,
    onPlaceOrder: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Imagen 200x200
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(RestaurantColors.AuthBackground),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(RestaurantColors.AuthAccent.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Fastfood,
                    contentDescription = null,
                    tint = RestaurantColors.AuthAccent,
                    modifier = Modifier.size(48.dp),
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        RestText(
            text = "Nota de pedido",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = RestaurantColors.OnSurface,
        )

        Spacer(Modifier.height(16.dp))
        HorizontalDivider(color = RestaurantColors.Divider)
        Spacer(Modifier.height(8.dp))

        // Lista de items del carrito
        state.cartItems.forEach { cartItem ->
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Emoji de categoría
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(RestaurantColors.AuthBackground),
                    contentAlignment = Alignment.Center,
                ) {
                    RestText(
                        text = when (cartItem.item.category.name) {
                            "ENTRADA"    -> "🥗"
                            "SEGUNDO"    -> "🍽️"
                            else         -> "⭐"
                        },
                        fontSize = 18.sp,
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    RestText(
                        text = cartItem.item.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = RestaurantColors.OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    RestText(
                        text = "S/. ${(cartItem.item.price * cartItem.quantity).formatPrice()}",
                        fontSize = 13.sp,
                        color = RestaurantColors.Primary,
                        fontWeight = FontWeight.Medium,
                    )
                }
                Spacer(Modifier.width(8.dp))

                // Controles − cantidad +
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    IconButton(
                        onClick = { onDecrease(cartItem.item) },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.SurfaceVariant),
                    ) {
                        Icon(Icons.Filled.Remove, "Quitar", tint = RestaurantColors.OnSurface, modifier = Modifier.size(16.dp))
                    }
                    RestText(
                        text = cartItem.quantity.toString(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = RestaurantColors.OnSurface,
                        modifier = Modifier.width(26.dp),
                        textAlign = TextAlign.Center,
                    )
                    IconButton(
                        onClick = { onIncrease(cartItem.item) },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.Primary),
                    ) {
                        Icon(Icons.Filled.Add, "Agregar", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(color = RestaurantColors.Divider.copy(alpha = 0.5f))
        }

        Spacer(Modifier.height(16.dp))

        // Total
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RestText(text = "Total", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RestaurantColors.OnSurface)
            RestText(text = "S/. ${state.total.formatPrice()}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = RestaurantColors.Primary)
        }

        Spacer(Modifier.height(20.dp))

        // Botón realizar pedido
        androidx.compose.material3.Button(
            onClick = onPlaceOrder,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = RestaurantColors.Primary,
                contentColor = Color.White,
            ),
        ) {
            RestText(
                text = "Realizar pedido",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}
