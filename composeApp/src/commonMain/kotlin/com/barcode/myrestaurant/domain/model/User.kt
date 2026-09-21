package com.barcode.myrestaurant.domain.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val phoneCountry: String,
    val avatarUrl: String?,
    val status: String,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val locale: String,
    val timezone: String,
    val lastLoginAt: String?,
    val createdAt: String,
    // Token
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val refreshToken: String,
    val refreshExpiresIn: Int,
    val sessionId: String,
) {
    val fullName get() = "$firstName $lastName".trim()
    val initials get() = buildString {
        firstName.firstOrNull()?.let { append(it.uppercaseChar()) }
        lastName.firstOrNull()?.let { append(it.uppercaseChar()) }
    }.ifEmpty { email.firstOrNull()?.uppercaseChar()?.toString() ?: "?" }
}
