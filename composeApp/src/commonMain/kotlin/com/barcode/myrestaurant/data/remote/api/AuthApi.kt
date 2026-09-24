package com.barcode.myrestaurant.data.remote.api

import com.barcode.myrestaurant.data.remote.dto.LoginRequestDto
import com.barcode.myrestaurant.data.remote.dto.LoginResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

private const val BASE_URL = "https://restaurant2.barcode-dev.in"

class AuthApi(private val client: HttpClient) {

    suspend fun login(request: LoginRequestDto): LoginResponseDto {
        val response = client.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (!response.status.isSuccess()) {
            val errorBody = response.bodyAsText()
            throw Exception("Error ${response.status.value}: $errorBody")
        }
        return response.body()
    }
}
