package com.barcode.myrestaurant.data.local

import com.barcode.myrestaurant.domain.model.User
import com.russhwolf.settings.Settings

class UserPreferences(private val settings: Settings) {

    companion object {
        private const val KEY_ACCESS_TOKEN      = "auth_access_token"
        private const val KEY_REFRESH_TOKEN     = "auth_refresh_token"
        private const val KEY_SESSION_ID        = "auth_session_id"
        private const val KEY_USER_ID           = "auth_user_id"
        private const val KEY_FIRST_NAME        = "auth_first_name"
        private const val KEY_LAST_NAME         = "auth_last_name"
        private const val KEY_EMAIL             = "auth_email"
        private const val KEY_PHONE             = "auth_phone"
        private const val KEY_PHONE_COUNTRY     = "auth_phone_country"
        private const val KEY_AVATAR_URL        = "auth_avatar_url"
        private const val KEY_STATUS            = "auth_status"
        private const val KEY_EMAIL_VERIFIED    = "auth_email_verified"
        private const val KEY_PHONE_VERIFIED    = "auth_phone_verified"
        private const val KEY_LOCALE            = "auth_locale"
        private const val KEY_TIMEZONE          = "auth_timezone"
        private const val KEY_LAST_LOGIN_AT     = "auth_last_login_at"
        private const val KEY_CREATED_AT        = "auth_created_at"
        private const val KEY_TOKEN_TYPE        = "auth_token_type"
        private const val KEY_EXPIRES_IN        = "auth_expires_in"
        private const val KEY_REFRESH_EXPIRES   = "auth_refresh_expires_in"
    }

    fun saveUser(user: User) {
        with(settings) {
            putString(KEY_ACCESS_TOKEN,   user.accessToken)
            putString(KEY_REFRESH_TOKEN,  user.refreshToken)
            putString(KEY_SESSION_ID,     user.sessionId)
            putString(KEY_USER_ID,        user.id)
            putString(KEY_FIRST_NAME,     user.firstName)
            putString(KEY_LAST_NAME,      user.lastName)
            putString(KEY_EMAIL,          user.email)
            putString(KEY_PHONE,          user.phone)
            putString(KEY_PHONE_COUNTRY,  user.phoneCountry)
            putString(KEY_AVATAR_URL,     user.avatarUrl ?: "")
            putString(KEY_STATUS,         user.status)
            putBoolean(KEY_EMAIL_VERIFIED, user.emailVerified)
            putBoolean(KEY_PHONE_VERIFIED, user.phoneVerified)
            putString(KEY_LOCALE,         user.locale)
            putString(KEY_TIMEZONE,       user.timezone)
            putString(KEY_LAST_LOGIN_AT,  user.lastLoginAt ?: "")
            putString(KEY_CREATED_AT,     user.createdAt)
            putString(KEY_TOKEN_TYPE,     user.tokenType)
            putInt(KEY_EXPIRES_IN,        user.expiresIn)
            putInt(KEY_REFRESH_EXPIRES,   user.refreshExpiresIn)
            // compat keys usados por ProfileTab
            putString("EMAIL", user.email)
            putString("NAME",  user.fullName)
            putString("PHONE", user.phone)
        }
    }

    fun getUser(): User? {
        val token = settings.getStringOrNull(KEY_ACCESS_TOKEN) ?: return null
        return User(
            id               = settings.getString(KEY_USER_ID, ""),
            firstName        = settings.getString(KEY_FIRST_NAME, ""),
            lastName         = settings.getString(KEY_LAST_NAME, ""),
            email            = settings.getString(KEY_EMAIL, ""),
            phone            = settings.getString(KEY_PHONE, ""),
            phoneCountry     = settings.getString(KEY_PHONE_COUNTRY, ""),
            avatarUrl        = settings.getString(KEY_AVATAR_URL, "").ifEmpty { null },
            status           = settings.getString(KEY_STATUS, ""),
            emailVerified    = settings.getBoolean(KEY_EMAIL_VERIFIED, false),
            phoneVerified    = settings.getBoolean(KEY_PHONE_VERIFIED, false),
            locale           = settings.getString(KEY_LOCALE, ""),
            timezone         = settings.getString(KEY_TIMEZONE, ""),
            lastLoginAt      = settings.getString(KEY_LAST_LOGIN_AT, "").ifEmpty { null },
            createdAt        = settings.getString(KEY_CREATED_AT, ""),
            accessToken      = token,
            tokenType        = settings.getString(KEY_TOKEN_TYPE, "Bearer"),
            expiresIn        = settings.getInt(KEY_EXPIRES_IN, 0),
            refreshToken     = settings.getString(KEY_REFRESH_TOKEN, ""),
            refreshExpiresIn = settings.getInt(KEY_REFRESH_EXPIRES, 0),
            sessionId        = settings.getString(KEY_SESSION_ID, ""),
        )
    }

    fun getAccessToken(): String? = settings.getStringOrNull(KEY_ACCESS_TOKEN)

    fun isLoggedIn(): Boolean = getAccessToken() != null

    fun clearUser() = settings.clear()
}
