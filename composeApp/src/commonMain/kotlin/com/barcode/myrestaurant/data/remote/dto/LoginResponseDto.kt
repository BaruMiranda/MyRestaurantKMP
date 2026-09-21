package com.barcode.myrestaurant.data.remote.dto

import com.barcode.myrestaurant.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val success: Boolean,
    val data: AuthDataDto,
)

@Serializable
data class AuthDataDto(
    val user: UserDto,
    val token: TokenDto,
)

@Serializable
data class UserDto(
    val id: String,
    @SerialName("first_name")     val firstName: String,
    @SerialName("last_name")      val lastName: String,
    val email: String,
    val phone: String,
    @SerialName("phone_country")  val phoneCountry: String,
    @SerialName("avatar_url")     val avatarUrl: String?,
    val status: String,
    @SerialName("email_verified") val emailVerified: Boolean,
    @SerialName("phone_verified") val phoneVerified: Boolean,
    val locale: String,
    val timezone: String,
    @SerialName("last_login_at")  val lastLoginAt: String?,
    @SerialName("created_at")     val createdAt: String,
)

@Serializable
data class TokenDto(
    @SerialName("access_token")      val accessToken: String,
    @SerialName("token_type")        val tokenType: String,
    @SerialName("expires_in")        val expiresIn: Int,
    @SerialName("refresh_token")     val refreshToken: String,
    @SerialName("refresh_expires_in") val refreshExpiresIn: Int,
    @SerialName("session_id")        val sessionId: String,
)

fun AuthDataDto.toDomain() = User(
    id             = user.id,
    firstName      = user.firstName,
    lastName       = user.lastName,
    email          = user.email,
    phone          = user.phone,
    phoneCountry   = user.phoneCountry,
    avatarUrl      = user.avatarUrl,
    status         = user.status,
    emailVerified  = user.emailVerified,
    phoneVerified  = user.phoneVerified,
    locale         = user.locale,
    timezone       = user.timezone,
    lastLoginAt    = user.lastLoginAt,
    createdAt      = user.createdAt,
    accessToken    = token.accessToken,
    tokenType      = token.tokenType,
    expiresIn      = token.expiresIn,
    refreshToken   = token.refreshToken,
    refreshExpiresIn = token.refreshExpiresIn,
    sessionId      = token.sessionId,
)
