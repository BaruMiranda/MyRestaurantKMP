package com.barcode.myrestaurant.screen.profile

import androidx.lifecycle.ViewModel
import com.barcode.myrestaurant.data.local.UserPreferences
import com.barcode.myrestaurant.screen.auth.LoginScreen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(private val prefs: UserPreferences) : ViewModel() {

    private val settings = Settings()

    val customerTypes: List<String> = listOf("Cliente Regular", "Cliente VIP", "Cliente Empresarial")

    private val _uiState = MutableStateFlow(loadInitialState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private fun loadInitialState(): ProfileUiState {
        val name  = settings.getString(LoginScreen.KEY_NAME, "")
        val phone = settings.getString(LoginScreen.KEY_PHONE, "")
        val email = settings.getString(LoginScreen.KEY_EMAIL, "")
        val type  = settings.getString(LoginScreen.KEY_CUSTOMER_TYPE, "Cliente Regular")
        return ProfileUiState(
            name         = name,
            phone        = phone,
            email        = email,
            customerType = type,
            isVip        = type == "Cliente VIP",
        )
    }

    fun onNameChange(value: String) =
        _uiState.update { it.copy(name = value, nameError = null, saved = false) }

    fun onPhoneChange(value: String) =
        _uiState.update { it.copy(phone = value, saved = false) }

    fun onCustomerTypeChange(value: String) =
        _uiState.update { it.copy(customerType = value, isVip = value == "Cliente VIP", saved = false) }

    fun saveChanges() {
        val state = _uiState.value
        if (state.name.isBlank()) {
            _uiState.update { it.copy(nameError = "El nombre no puede estar vacío") }
            return
        }
        settings.putString(LoginScreen.KEY_NAME, state.name)
        settings.putString(LoginScreen.KEY_PHONE, state.phone)
        settings.putString(LoginScreen.KEY_CUSTOMER_TYPE, state.customerType)
        settings.putBoolean(LoginScreen.KEY_VIP, state.isVip)
        _uiState.update { it.copy(saved = true, nameError = null) }
    }

    fun logout() {
        prefs.clearSession()
        _uiState.update { it.copy(isLoggedOut = true) }
    }
}
