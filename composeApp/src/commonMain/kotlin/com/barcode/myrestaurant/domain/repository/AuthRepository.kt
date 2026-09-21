package com.barcode.myrestaurant.domain.repository

import com.barcode.myrestaurant.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
}
