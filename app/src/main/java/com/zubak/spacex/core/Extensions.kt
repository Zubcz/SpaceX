package com.zubak.spacex.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.security.MessageDigest

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}

fun String.sha1(): String {
    val sha1 = MessageDigest.getInstance("SHA-1")
    return sha1.digest(this.toByteArray()).toHex()
}
