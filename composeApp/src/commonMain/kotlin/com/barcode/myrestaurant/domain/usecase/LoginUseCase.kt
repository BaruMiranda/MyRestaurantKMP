package com.barcode.myrestaurant.domain.usecase

import com.barcode.myrestaurant.domain.model.User
import com.barcode.myrestaurant.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank()) return Result.failure(IllegalArgumentException("El correo es requerido"))
        if (password.isBlank()) return Result.failure(IllegalArgumentException("La contraseña es requerida"))
        return repository.login(email.trim(), password)
    }
}
