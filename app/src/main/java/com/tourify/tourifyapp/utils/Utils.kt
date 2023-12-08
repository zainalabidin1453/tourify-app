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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

fun checkKeywords(keywords: String): Boolean {
    val wordCount = keywords.trim().split("\\s+".toRegex()).joinToString("").length
    return wordCount >= 4
}

fun modifyDateRange(startDate: String, endDate: String): String {
    val inputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    if (startDate.isEmpty() && endDate.isEmpty()) {
        return ""
    } else if (startDate.isEmpty()) {
        val calEnd = Calendar.getInstance()
        calEnd.time = inputFormat.parse(endDate) ?: Date()
        return outputFormat.format(calEnd.time)
    } else if (endDate.isEmpty()) {
        val calStart = Calendar.getInstance()
        calStart.time = inputFormat.parse(startDate) ?: Date()
        return outputFormat.format(calStart.time)
    } else if (startDate == "Start Date") {
        return ""
    } else if (endDate == "End Date") {
        return ""
    }

    val calStart = Calendar.getInstance()
    val calEnd = Calendar.getInstance()

    calStart.time = inputFormat.parse(startDate) ?: Date()
    calEnd.time = inputFormat.parse(endDate) ?: Date()

    return if (calStart.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)) {
        if (calStart.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)) {
            if (calStart.time == calEnd.time) {
                outputFormat.format(calStart.time)
            } else {
                val sameStartDate = SimpleDateFormat("dd", Locale.getDefault()).format(calStart.time)
                val sameEndDate = SimpleDateFormat("dd", Locale.getDefault()).format(calEnd.time)
                val sameEndMonth = SimpleDateFormat("MMM", Locale.getDefault()).format(calEnd.time)
                "$sameStartDate - $sameEndDate $sameEndMonth ${calEnd.get(Calendar.YEAR)}"
            }
        } else {
            val difStartDate = SimpleDateFormat("dd", Locale.getDefault()).format(calStart.time)
            val difStartMonth = SimpleDateFormat("MMM", Locale.getDefault()).format(calStart.time)
            "$difStartDate $difStartMonth - ${outputFormat.format(calEnd.time)}"
        }
    } else {
        "${outputFormat.format(calStart.time)} - ${outputFormat.format(calEnd.time)}"
    }
}

fun isValidEndDate(date: String): Boolean {
    val dateFormat1 = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val dateFormat2 = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val dateFormatList = listOf(dateFormat1, dateFormat2)

    for (format in dateFormatList) {
        try {
            format.isLenient = false
            format.parse(date)
            return true
        }  catch (_: Exception) { }
    }
    return false
}

fun changeDateToLong(date: String): Long {
    if (date.isEmpty()) {
        return -1L
    }

    val inputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val calDate = Calendar.getInstance()
    calDate.time = inputFormat.parse(date) ?: Date()

    return calDate.timeInMillis
}
