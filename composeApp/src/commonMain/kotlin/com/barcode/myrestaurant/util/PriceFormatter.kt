package com.barcode.myrestaurant.util

fun Double.formatPrice(): String {
    val cents = kotlin.math.round(this * 100).toLong()
    val intPart = cents / 100
    val decPart = cents % 100
    return "$intPart.${decPart.toString().padStart(2, '0')}"
}
