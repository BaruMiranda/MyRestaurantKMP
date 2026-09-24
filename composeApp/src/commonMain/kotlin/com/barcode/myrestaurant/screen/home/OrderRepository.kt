package com.barcode.myrestaurant.screen.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object OrderRepository {
    private val _isActive = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive.asStateFlow()

    fun place() { _isActive.value = true }

    fun cancel() {
        _isActive.value = false
        CartRepository.clear()
    }
}
