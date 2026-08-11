package com.barcode.myrestaurant

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform