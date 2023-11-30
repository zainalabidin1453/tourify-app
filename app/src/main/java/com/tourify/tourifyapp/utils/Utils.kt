package com.tourify.tourifyapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.TextUtils
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.text.NumberFormat
import java.util.Locale

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    val regex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$")
    return regex.matches(password)
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

fun modifyNumberFormat(numberValue: String): String {
    val number = numberValue.toDoubleOrNull() ?: return ""
    return when {
        number >= 1_000_000_000_000 -> String.format("%.0ft", number / 1_000_000_000_000)
        number >= 1_000_000_000 -> String.format("%.0fb", number / 1_000_000_000)
        number >= 1_000_000 -> String.format("%.0fm", number / 1_000_000)
        number >= 1_000 -> String.format("%.0fk", number / 1_000)
        else -> number.toInt().toString()
    }
}

fun modifyMoneyFormat(total: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    formatter.maximumFractionDigits = 0
    return "${formatter.format(total.toLong()).replace("IDR", "").trim()},-"
}