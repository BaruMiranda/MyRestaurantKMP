package com.barcode.myrestaurant.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barcode.myrestaurant.screen.home.CartRepository
import com.barcode.myrestaurant.screen.home.MenuItem
import com.barcode.myrestaurant.screen.home.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(CartRepository.items, OrderRepository.isActive) { items, active ->
                _uiState.value.copy(items = items, activeOrder = active)
            }.collect { _uiState.value = it }
        }
    }

    fun increase(item: MenuItem) = CartRepository.increase(item)
    fun decrease(item: MenuItem) = CartRepository.decrease(item)

    fun cancelOrder() = OrderRepository.cancel()
}
