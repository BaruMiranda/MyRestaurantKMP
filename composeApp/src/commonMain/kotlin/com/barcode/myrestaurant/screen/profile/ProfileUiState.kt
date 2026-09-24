package com.barcode.myrestaurant.screen.profile

data class ProfileUiState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val customerType: String = "Cliente Regular",
    val isVip: Boolean = false,
    val saved: Boolean = false,
    val nameError: String? = null,
    val isLoggedOut: Boolean = false,
)
