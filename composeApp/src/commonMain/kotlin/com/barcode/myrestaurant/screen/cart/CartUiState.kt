package com.barcode.myrestaurant.screen.cart

import com.barcode.myrestaurant.screen.home.CartItem

enum class OrderStatus(val label: String, val description: String) {
    PENDIENTE(      "Pendiente",       "Tu pedido está esperando confirmación"),
    CONFIRMADO(     "Confirmado",      "El restaurante aceptó tu pedido"),
    EN_PREPARACION( "En preparación",  "Tu pedido se está preparando"),
    LISTO(          "Listo",           "Tu pedido está listo para entregar"),
    ENTREGADO(      "Entregado",       "¡Pedido entregado con éxito!"),
}

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val activeOrder: Boolean = false,
    val orderStatus: OrderStatus = OrderStatus.PENDIENTE,
) {
    val total: Double get() = items.sumOf { it.item.price * it.quantity }
    val totalUnits: Int get() = items.sumOf { it.quantity }
    val isEmpty: Boolean get() = items.isEmpty()
}
