package com.barcode.myrestaurant.di

import com.barcode.myrestaurant.data.local.UserPreferences
import com.barcode.myrestaurant.data.remote.api.AuthApi
import com.barcode.myrestaurant.data.remote.createHttpClient
import com.barcode.myrestaurant.data.repository.AuthRepositoryImpl
import com.barcode.myrestaurant.domain.usecase.LoginUseCase
import com.russhwolf.settings.Settings

object AppContainer {
    private val httpClient     by lazy { createHttpClient() }
    private val authApi        by lazy { AuthApi(httpClient) }
    private val authRepository by lazy { AuthRepositoryImpl(authApi) }

    val userPreferences by lazy { UserPreferences(Settings()) }
    val loginUseCase    by lazy { LoginUseCase(authRepository) }
}
