package com.barcode.myrestaurant.screen.auth

import com.barcode.myrestaurant.domain.model.User

data class LoginUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)