package com.barcode.myrestaurant.data.remote

import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual suspend fun getFcmToken(): String = suspendCoroutine { continuation ->
    FirebaseMessaging.getInstance().token
        .addOnSuccessListener { token -> continuation.resume(token) }
        .addOnFailureListener { e -> continuation.resumeWithException(e) }
}
