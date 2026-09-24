package com.barcode.myrestaurant.screen.home

data class HomeUiState(
    val entradas: List<MenuItem> = emptyList(),
    val segundos: List<MenuItem> = emptyList(),
    val alaCartaItems: List<MenuItem> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val isBottomSheetVisible: Boolean = false,
) {
    val totalItems: Int get() = cartItems.sumOf { it.quantity }
    val total: Double get() = cartItems.sumOf { it.item.price * it.quantity }
    val hasItems: Boolean get() = cartItems.isNotEmpty()
}
