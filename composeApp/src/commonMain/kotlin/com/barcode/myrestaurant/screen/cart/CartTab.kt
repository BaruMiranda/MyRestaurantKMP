package com.barcode.myrestaurant.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.barcode.myrestaurant.screen.home.CartItem
import com.barcode.myrestaurant.screen.home.MenuItem
import com.barcode.myrestaurant.screen.home.OrderRepository
import com.barcode.myrestaurant.ui.components.AppHeader
import com.barcode.myrestaurant.util.formatPrice
import com.barcode.myrestaurant.ui.components.RestFilledButton
import com.barcode.myrestaurant.ui.components.RestOutlinedButton
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestText

object CartTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.ShoppingCart)
            return remember { TabOptions(index = 1u, title = "Carrito", icon = icon) }
        }

    @Composable
    override fun Content() {
        val viewModel = viewModel<CartViewModel>(key = "CartViewModel") { CartViewModel() }
        val state by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            AppHeader(
                title = if (state.activeOrder) "Estado del pedido" else "Mi Carrito",
                subtitle = if (state.activeOrder) state.orderStatus.description
                           else "Revisa tu pedido antes de confirmar",
            )

            when {
                // ── Pedido activo: mostrar tracker + items ────────────────
                state.activeOrder -> {
                    Spacer(Modifier.height(24.dp))
                    OrderStatusTracker(
                        currentStatus = state.orderStatus,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                    Spacer(Modifier.height(24.dp))
                    SectionLabel(
                        text = "Detalle del pedido",
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                    Spacer(Modifier.height(12.dp))
                    state.items.forEach { cartItem ->
                        CartItemRow(
                            cartItem = cartItem,
                            readOnly = true,
                            onIncrease = {},
                            onDecrease = {},
                            modifier = Modifier.padding(horizontal = 20.dp),
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    Spacer(Modifier.height(16.dp))
                    TotalRow(total = state.total, modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(Modifier.height(24.dp))
                    RestOutlinedButton(
                        text = "Cancelar pedido",
                        onClick = viewModel::cancelOrder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        borderColor = RestaurantColors.Error,
                        contentColor = RestaurantColors.Error,
                    )
                    Spacer(Modifier.height(32.dp))
                }

                // ── Carrito vacío ─────────────────────────────────────────
                state.isEmpty -> {
                    Spacer(Modifier.height(60.dp))
                    EmptyCartState()
                }

                // ── Carrito con items, sin pedido activo ──────────────────
                else -> {
                    Spacer(Modifier.height(20.dp))
                    SectionLabel(
                        text = "Productos seleccionados",
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                    Spacer(Modifier.height(12.dp))
                    state.items.forEach { cartItem ->
                        CartItemRow(
                            cartItem = cartItem,
                            readOnly = false,
                            onIncrease = { viewModel.increase(cartItem.item) },
                            onDecrease = { viewModel.decrease(cartItem.item) },
                            modifier = Modifier.padding(horizontal = 20.dp),
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    Spacer(Modifier.height(20.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = RestaurantColors.Divider,
                    )
                    Spacer(Modifier.height(16.dp))
                    TotalRow(total = state.total, modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(Modifier.height(24.dp))
                    RestFilledButton(
                        text = "Realizar pedido",
                        onClick = { OrderRepository.place() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(50.dp),
                        containerColor = RestaurantColors.Primary,
                    )
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

// ── Composables privados ─────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    RestText(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = RestaurantColors.OnSurfaceVariant,
        modifier = modifier,
    )
}

@Composable
private fun CartItemRow(
    cartItem: CartItem,
    readOnly: Boolean,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Ícono de categoría
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(RestaurantColors.AuthBackground),
                contentAlignment = Alignment.Center,
            ) {
                RestText(
                    text = when (cartItem.item.category.name) {
                        "ENTRADA"    -> "🥗"
                        "SEGUNDO"    -> "🍽️"
                        else         -> "⭐"
                    },
                    fontSize = 20.sp,
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
                Spacer(Modifier.height(2.dp))
                RestText(
                    text = "S/. ${(cartItem.item.price * cartItem.quantity).formatPrice()}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = RestaurantColors.Primary,
                )
            }

            if (readOnly) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(RestaurantColors.AuthAccent),
                    contentAlignment = Alignment.Center,
                ) {
                    RestText(
                        text = "×${cartItem.quantity}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = RestaurantColors.OnSecondary,
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    IconButton(
                        onClick = onDecrease,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.SurfaceVariant),
                    ) {
                        Icon(Icons.Filled.Remove, null, tint = RestaurantColors.OnSurface, modifier = Modifier.size(14.dp))
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
                        onClick = onIncrease,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(RestaurantColors.Primary),
                    ) {
                        Icon(Icons.Filled.Add, null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TotalRow(total: Double, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RestText(text = "Total", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RestaurantColors.OnSurface)
        RestText(text = "S/. ${total.formatPrice()}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = RestaurantColors.Primary)
    }
}

@Composable
private fun EmptyCartState() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(RestaurantColors.SurfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null,
                tint = RestaurantColors.Border,
                modifier = Modifier.size(52.dp),
            )
        }
        Spacer(Modifier.height(20.dp))
        RestText(
            text = "Tu carrito está vacío",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = RestaurantColors.OnSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        RestText(
            text = "Agrega productos desde la pantalla de inicio",
            fontSize = 14.sp,
            color = RestaurantColors.OnSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
        )
    }
}

@Composable
private fun OrderStatusTracker(
    currentStatus: OrderStatus,
    modifier: Modifier = Modifier,
) {
    val steps = OrderStatus.entries
    val currentIndex = steps.indexOf(currentStatus)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            RestText(
                text = currentStatus.label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RestaurantColors.Primary,
            )
            Spacer(Modifier.height(4.dp))
            RestText(
                text = currentStatus.description,
                fontSize = 13.sp,
                color = RestaurantColors.OnSurfaceVariant,
            )
            Spacer(Modifier.height(20.dp))

            // Barra de progreso con círculos
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                steps.forEachIndexed { index, step ->
                    val isDone = index <= currentIndex
                    val isCurrent = index == currentIndex

                    // Círculo del paso
                    Box(
                        modifier = Modifier
                            .size(if (isCurrent) 32.dp else 26.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isDone && !isCurrent -> RestaurantColors.Primary
                                    isCurrent            -> RestaurantColors.AuthBackground
                                    else                 -> RestaurantColors.Border
                                }
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (isDone && !isCurrent) {
                            Icon(Icons.Filled.CheckCircle, null, tint = Color.White, modifier = Modifier.size(16.dp))
                        } else {
                            RestText(
                                text = (index + 1).toString(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCurrent) RestaurantColors.AuthAccent else Color.White,
                            )
                        }
                    }

                    // Línea conectora
                    if (index < steps.lastIndex) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(3.dp)
                                .background(
                                    if (index < currentIndex) RestaurantColors.Primary
                                    else RestaurantColors.Border
                                ),
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Etiquetas debajo de los círculos
            Row(modifier = Modifier.fillMaxWidth()) {
                steps.forEachIndexed { index, step ->
                    RestText(
                        text = step.label,
                        fontSize = 9.sp,
                        fontWeight = if (index == currentIndex) FontWeight.Bold else FontWeight.Normal,
                        color = if (index <= currentIndex) RestaurantColors.Primary else RestaurantColors.Border,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}
