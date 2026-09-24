package com.barcode.myrestaurant.screen.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CartRepository {

    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items: StateFlow<List<CartItem>> = _items.asStateFlow()

    val total: Double get() = _items.value.sumOf { it.item.price * it.quantity }
    val totalUnits: Int get() = _items.value.sumOf { it.quantity }
    val hasItems: Boolean get() = _items.value.isNotEmpty()

    fun add(item: MenuItem) {
        _items.update { list ->
            val existing = list.find { it.item.id == item.id }
            if (existing != null) list.map { if (it.item.id == item.id) it.copy(quantity = it.quantity + 1) else it }
            else list + CartItem(item, 1)
        }
    }

    fun increase(item: MenuItem) = add(item)

    fun decrease(item: MenuItem) {
        _items.update { list ->
            list.mapNotNull {
                if (it.item.id == item.id) {
                    if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
                } else it
            }
        }
    }

    fun clear() = _items.update { emptyList() }
}
