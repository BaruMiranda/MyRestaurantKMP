package com.barcode.myrestaurant.screen.home

import androidx.lifecycle.ViewModel
import com.barcode.myrestaurant.data.local.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(private val prefs: UserPreferences) : ViewModel() {

    val userName: String get() = prefs.getUser()?.firstName ?: ""

    private val _uiState = MutableStateFlow(
        HomeUiState(
            entradas      = mockEntradas,
            segundos      = mockSegundos,
            alaCartaItems = mockALaCarta,
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        // Solo actualiza cartItems — preserva isBottomSheetVisible y el resto del estado
        viewModelScope.launch {
            CartRepository.items.collect { items ->
                _uiState.update { it.copy(cartItems = items) }
            }
        }
    }

    fun addToCart(item: MenuItem) = CartRepository.add(item)
    fun increaseQty(item: MenuItem) = CartRepository.increase(item)
    fun decreaseQty(item: MenuItem) = CartRepository.decrease(item)

    fun showBottomSheet() = _uiState.update { it.copy(isBottomSheetVisible = true) }
    fun hideBottomSheet() = _uiState.update { it.copy(isBottomSheetVisible = false) }
    fun placeOrder() {
        OrderRepository.place()
        hideBottomSheet()
    }
}

// ── Mock data ────────────────────────────────────────────────────────────────

private val mockEntradas = listOf(
    MenuItem("e1", "Ceviche de pescado",   "Fresco ceviche marinado en limón con ají limo", 15.0, MenuCategory.ENTRADA),
    MenuItem("e2", "Causa limeña",         "Causa de papa amarilla rellena de atún",        12.0, MenuCategory.ENTRADA),
    MenuItem("e3", "Tequeños de queso",    "Crujientes tequeños rellenos de queso gouda",   10.0, MenuCategory.ENTRADA),
    MenuItem("e4", "Anticuchos",           "Corazón de res a la parrilla con ají panca",    18.0, MenuCategory.ENTRADA),
    MenuItem("e5", "Papa a la huancaína", "Papa cocida con salsa de queso y ají amarillo", 10.0, MenuCategory.ENTRADA),
)

private val mockSegundos = listOf(
    MenuItem("s1", "Lomo saltado",        "Tiras de res salteadas con papas fritas y arroz",    28.0, MenuCategory.SEGUNDO),
    MenuItem("s2", "Ají de gallina",      "Pollo desmenuzado en crema de ají amarillo",          22.0, MenuCategory.SEGUNDO),
    MenuItem("s3", "Pollo a la brasa ¼", "Cuarto de pollo a la brasa con ensalada y papas",     25.0, MenuCategory.SEGUNDO),
    MenuItem("s4", "Tallarín saltado",    "Fideos salteados con verduras y res",                 20.0, MenuCategory.SEGUNDO),
    MenuItem("s5", "Seco de res",         "Carne guisada con chicha de jora y cilantro",         24.0, MenuCategory.SEGUNDO),
    MenuItem("s6", "Estofado de pollo",   "Pollo guisado con aceitunas y papas",                 22.0, MenuCategory.SEGUNDO),
    MenuItem("s7", "Arroz con mariscos", "Arroz cremoso con mix de mariscos frescos",            30.0, MenuCategory.SEGUNDO),
)

private val mockALaCarta = listOf(
    MenuItem("c1", "Ceviche mixto",             "Mix de pescado, pulpo y mariscos en leche de tigre",  35.0, MenuCategory.A_LA_CARTA),
    MenuItem("c2", "Parihuela",                 "Sopa de mariscos con ají panca y chicha de jora",     40.0, MenuCategory.A_LA_CARTA),
    MenuItem("c3", "Arroz chaufa de mariscos", "Arroz salteado al wok con mix de mariscos",           32.0, MenuCategory.A_LA_CARTA),
    MenuItem("c4", "Chupe de camarones",        "Sopa cremosa de camarones con huevo y leche",         38.0, MenuCategory.A_LA_CARTA),
    MenuItem("c5", "Chicharrón de pescado",     "Pescado rebozado y frito con salsa criolla",          28.0, MenuCategory.A_LA_CARTA),
    MenuItem("c6", "Tiradito de lenguado",      "Finas láminas de lenguado con salsa de ají amarillo", 35.0, MenuCategory.A_LA_CARTA),
)
