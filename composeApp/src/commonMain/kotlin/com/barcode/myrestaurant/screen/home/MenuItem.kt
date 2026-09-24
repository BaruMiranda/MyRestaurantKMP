package com.barcode.myrestaurant.screen.home

enum class MenuCategory { ENTRADA, SEGUNDO, A_LA_CARTA }

data class MenuItem(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: MenuCategory,
)

data class CartItem(
    val item: MenuItem,
    val quantity: Int,
)
