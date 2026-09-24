package com.barcode.myrestaurant.data.repository

import com.barcode.myrestaurant.data.remote.api.AuthApi
import com.barcode.myrestaurant.data.remote.dto.LoginRequestDto
import com.barcode.myrestaurant.data.remote.dto.toDomain
import com.barcode.myrestaurant.data.remote.getFcmToken
import com.barcode.myrestaurant.domain.model.User
import com.barcode.myrestaurant.domain.repository.AuthRepository

private const val DEVICE_NAME = "MyRestaurant App"
private const val DEVICE_TYPE = "ANDROID"

class AuthRepositoryImpl(private val api: AuthApi) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = api.login(
                LoginRequestDto(
                    email      = email,
                    password   = password,
                    deviceName = DEVICE_NAME,
                    deviceType = DEVICE_TYPE,
                    fcmToken   = getFcmToken(),
                )
            )
            if (response.success) {
                Result.success(response.data.toDomain())
            } else {
                Result.failure(Exception("El servidor rechazó la solicitud"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
