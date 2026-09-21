package com.barcode.myrestaurant.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String,
    @SerialName("device_name") val deviceName: String,
    @SerialName("device_type") val deviceType: String,
    @SerialName("fcm_token")   val fcmToken: String,
)
