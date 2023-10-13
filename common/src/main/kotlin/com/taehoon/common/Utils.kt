package com.taehoon.common

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.security.MessageDigest
import kotlin.math.roundToInt


fun convertDpToPx(context: Context, dp: Int): Int {
    val density = context.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}

fun String.convertFileName(): String {
    try {
        val bytes = this@convertFileName.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digestBytes = md.digest(bytes)

        // 바이트 배열을 16진수 문자열로 변환
        val hexString = StringBuilder()
        for (byte in digestBytes) {
            val hex = Integer.toHexString(0xFF and byte.toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return this
    }
}


fun Bitmap.getKBSize(): Int = byteCount / 1024
fun File.getKBSize(): Int = length().toInt() / 1024