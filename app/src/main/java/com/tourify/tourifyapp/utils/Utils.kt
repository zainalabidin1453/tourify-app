package com.tourify.tourifyapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.constants.Constants
import com.tourify.tourifyapp.data.sources.LocationDetails
import com.tourify.tourifyapp.ui.theme.ColorChocolate
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorGreen
import com.tourify.tourifyapp.ui.theme.ColorInfo
import com.tourify.tourifyapp.ui.theme.ColorMaroon
import com.tourify.tourifyapp.ui.theme.ColorOrange
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWarning
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

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
                val sameStartDate =
                    SimpleDateFormat("dd", Locale.getDefault()).format(calStart.time)
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
        } catch (_: Exception) {
        }
    }
    return false
}

@RequiresApi(Build.VERSION_CODES.O)
fun trackTripWithDate(tripDate: String): Int {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return when (tripDate.count { it == ' ' }) {
        3 -> {
            // If format "dd MMM yyyy"
            val startDate = LocalDate.parse(tripDate, formatter)
            return if (LocalDate.now().isBefore(startDate)) 1 else 2
        }

        4 -> {
            // If format "dd - dd MMM yyyy"
            val dates = tripDate.split(" ")
            val startDate = LocalDate.parse("${dates[0]} ${dates[3]} ${dates[4]}", formatter)
            return if (LocalDate.now().isBefore(startDate)) 1 else 2
        }

        5 -> {
            // If format "dd MMM - dd MMM yyyy"
            val dates = tripDate.split(" - ")
            val startDateString = "${dates[0]} ${LocalDate.parse(dates[1], formatter).year}"
            val startDate = LocalDate.parse(startDateString, formatter)
            return if (LocalDate.now().isBefore(startDate)) 1 else 2
        }

        6 -> {
            // If format "dd MMM yyyy - dd MMM yyyy"
            val dates = tripDate.split(" - ")
            val startDateString = dates[0]
            val startDate = LocalDate.parse(startDateString, formatter)
            return if (LocalDate.now().isBefore(startDate)) 1 else 2
        }

        else -> 1
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCheckInDate(tripDate: String): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return when (tripDate.count { it == ' ' }) {
        3 -> {
            // If format "dd MMM yyyy"
            val startDate = LocalDate.parse(tripDate, formatter)
            return startDate.format(formatter)
        }

        4 -> {
            // If format "dd - dd MMM yyyy"
            val dates = tripDate.split(" ")
            val startDate = LocalDate.parse("${dates[0]} ${dates[3]} ${dates[4]}", formatter)
            return startDate.format(formatter)
        }

        5 -> {
            // If format "dd MMM - dd MMM yyyy"
            val dates = tripDate.split(" - ")
            val startDateString = "${dates[0]} ${LocalDate.parse(dates[1], formatter).year}"
            val startDate = LocalDate.parse(startDateString, formatter)
            return startDate.format(formatter)
        }

        6 -> {
            // If format "dd MMM yyyy - dd MMM yyyy"
            val dates = tripDate.split(" - ")
            val startDateString = dates[0]
            val startDate = LocalDate.parse(startDateString, formatter)
            return startDate.format(formatter)
        }

        else -> return tripDate.format(formatter)
    }
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

fun cutMessage(message: String): String {
    return if (message.length > 20) {
        "${message.substring(0, 17)}..."
    } else {
        message
    }
}

fun getDistanceLocation(currentLocation: LocationDetails, toLocation: LocationDetails): Int {
    val location1 = Location("Lokasi Sekarang")
    location1.latitude = currentLocation.lat
    location1.longitude = currentLocation.lon

    val location2 = Location("Tujuan")
    location2.latitude = toLocation.lat
    location2.longitude = toLocation.lon

    val distanceInMeters = location1.distanceTo(location2)
    val distanceInKm = distanceInMeters / 1000
    return distanceInKm.roundToInt()
}

fun getMyProvince(province: String): String {
    return when (province) {
        "Aceh" -> Constants.ACEH
        "North Sumatra", "Sumatera Utara" -> Constants.NORTH_SUMATRA
        "West Sumatra", "Sumatera Barat" -> Constants.WEST_SUMATRA
        "Riau" -> Constants.RIAU
        "Riau Islands", "Kepulauan Riau" -> Constants.RIAU_ISLANDS
        "Jambi" -> Constants.JAMBI
        "South Sumatra", "Sumatera Selatan" -> Constants.SOUTH_SUMATRA
        "Bangka Belitung Islands", "Kepulauan Bangka Belitung" -> Constants.BANGKA_BELITUNG
        "Bengkulu" -> Constants.BENGKULU
        "Lampung" -> Constants.LAMPUNG
        "Banten" -> Constants.BANTEN
        "West Java", "Jawa Barat" -> Constants.WEST_JAVA
        "DKI Jakarta" -> Constants.JAKARTA
        "Central Java", "Jawa Tengah" -> Constants.CENTRAL_JAVA
        "Special Region of Yogyakarta", "Daerah Istimewa Yogyakarta" -> Constants.YOGYAKARTA
        "East Java", "Jawa Timur" -> Constants.EAST_JAVA
        "Bali" -> Constants.BALI
        "West Nusa Tenggara", "Nusa Tenggara Barat" -> Constants.WEST_NUSA_TENGGARA
        "East Nusa Tenggara", "Nusa Tenggara Timur" -> Constants.EAST_NUSA_TENGGARA
        "West Kalimantan", "Kalimantan Barat" -> Constants.WEST_KALIMANTAN
        "Central Kalimantan", "Kalimantan Tengah" -> Constants.CENTRAL_KALIMANTAN
        "South Kalimantan", "Kalimantan Selatan" -> Constants.SOUTH_KALIMANTAN
        "East Kalimantan", "Kalimantan Timur" -> Constants.EAST_KALIMANTAN
        "North Kalimantan", "Kalimantan Utara" -> Constants.NORTH_KALIMANTAN
        "North Sulawesi", "Sulawesi Utara" -> Constants.NORTH_SULAWESI
        "Central Sulawesi", "Sulawesi Tengah" -> Constants.CENTRAL_SULAWESI
        "South Sulawesi", "Sulawesi Selatan" -> Constants.SOUTH_SULAWESI
        "Southeast Sulawesi", "Sulawesi Tenggara" -> Constants.SOUTHEAST_SULAWESI
        "Gorontalo" -> Constants.GORONTALO
        "West Sulawesi", "Sulawesi Barat" -> Constants.WEST_SULAWESI
        "Maluku" -> Constants.MALUKU
        "North Maluku", "Maluku Utara" -> Constants.NORTH_MALUKU
        "Papua", "Papua Barat", "Papua Selatan", "Papua Tengah", "Papua Pegunungan", "Papua Barat Daya" -> Constants.PAPUA
        else -> Constants.ACEH
    }
}

fun changeTime(time: String): String {
    val floatTime = time.toDouble()
    val hours = (floatTime * 24).toInt()
    val minutes = ((floatTime * 24 - hours) * 60).toInt()

    return "${String.format("%02d", hours)}:${String.format("%02d", minutes)}"
}

@RequiresApi(Build.VERSION_CODES.O)
fun checkStatusTime(openOn: String, closedOn: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val systemZoneId = ZoneId.systemDefault()
    val jakartaZoneId = ZoneId.of("Asia/Jakarta")

    val currentDateTime = LocalDateTime.now()

    val convertedTime =
        if (systemZoneId != jakartaZoneId) {
            val currentDateTimeJakarta = LocalDateTime.now(jakartaZoneId)
            val offset = systemZoneId.rules.getOffset(currentDateTime)
            val convertedDateTime = currentDateTime.plusSeconds(offset.totalSeconds.toLong())
            val diff =
                convertedDateTime.toLocalTime().toNanoOfDay() - currentDateTimeJakarta.toLocalTime()
                    .toNanoOfDay()
            currentDateTimeJakarta.plusNanos(diff)
        } else {
            currentDateTime
        }

    val openTime = LocalTime.parse(openOn, formatter)
    val closedTime = LocalTime.parse(closedOn, formatter)

    return when {
        convertedTime.toLocalTime().isAfter(openTime) && convertedTime.toLocalTime()
            .isBefore(closedTime) -> {
            true
        }

        else -> {
            false
        }
    }
}

fun cutLocation(location: String): String {
    val keywords = listOf("Kabupaten ", "Kota")
    var newLocation = location
    for (word in keywords) {
        if (location.contains(word, ignoreCase = true)) {
            newLocation = location.replace(word, "", ignoreCase = true)
            break
        }
    }
    return newLocation.trim()
}

fun colorRandom(): Color {
    val colorList = listOf(
        ColorPrimary.copy(0.5f),
        ColorWarning.copy(0.5f),
        ColorInfo.copy(0.5f),
        ColorDanger.copy(0.5f),
        ColorOrange.copy(0.5f),
        ColorGreen.copy(0.5f),
        ColorChocolate.copy(0.5f),
        ColorMaroon.copy(0.5f)
    )
    return colorList.random()
}

@SuppressLint("SimpleDateFormat")
fun calculateDay(startDate: String, endDate: String): Int {
    val sdf = SimpleDateFormat("dd MMM yyyy")
    val date1: Date = sdf.parse(startDate)!!
    val date2: Date = sdf.parse(endDate)!!
    val difference: Long = date2.time - date1.time
    val days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
    return days.toInt()
}

@SuppressLint("SimpleDateFormat")
fun calculateHours(startDate: String, endDate: String): Int {
    val sdf = SimpleDateFormat("dd MMM yyyy")
    val date1: Date = sdf.parse(startDate)!!
    val date2: Date = sdf.parse(endDate)!!

    val difference: Long = date2.time - date1.time
    val hours = TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS)
    return hours.toInt()
}

@RequiresApi(Build.VERSION_CODES.O)
fun modifyDateFormat(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return date.format(formatter)
}

val attractionKeywords = listOf(
    "pantai", "danau", "air", "ocean", "pesisir",
    "pasir", "hujan", "pulau", "resor",
    "tebing", "aliran",
    "eksotis", "selancar", "berjemur", "kapal"
)
val adventureKeywords = listOf(
    "gunung", "hutan", "gurun", "reruntuhan", "petualangan",
    "eksplorasi", "pencarian", "matahari terbit", "matahari terbenam",
    "panorama", "alam", "pelabuhan", "bukit",
    "lereng", "perjalanan", "pemancingan", "rafting",
    "trekking", "penjelajahan", "safari"
)
val recreationKeywords = listOf(
    "bersejarah", "museum", "seni", "budaya", "arsitektur",
    "warisan", "purbakala", "galery", "monumen", "pameran",
    "koleksi", "karya", "taman", "bersejarah",
    "kuno", "situs", "penting", "tempat"
)
val educationCultureKeywords = listOf(
    "edukasi", "budaya", "tradisi", "belajar", "sejarah",
    "kreativitas", "kebudayaan", "pengetahuan", "lokal", "tradisional",
    "adat", "ritual", "rupa", "folklor", "situs", "seni",
    "event", "karya", "perpustakaan", "pembelajaran", "warisan"
)
val honeymoonKeywords = listOf(
    "resor", "romantis", "privat", "sunset",
    "villa", "destinasi", "pasangan", "momen", "penginapan",
    "pantai", "honeymoon", "liburan", "getaway", "tenang",
    "santai", "terpencil", "pasir", "indah", "bulan", "madu"
)
val familyVacationKeywords = listOf(
    "keluarga", "anak", "anak-anak", "bersama", "ramah", "kegiatan",
    "rekreasi", "piknik", "taman", "bermain",
    "wisata", "alami", "area", "objek", "bersantai", "family"
)
val iconicViewKeywords = listOf(
    "landmark", "monumen", "gedung", "bersejarah", "ikon",
    "struktur", "spot", "simbol", "tempat", "populer",
    "patung", "ikonis", "situe",
    "simbolis", "sejarah"
)
val photoVideoKeywords = listOf(
    "landscape", "potret", "keindahan",
    "foto", "panorama",
    "video", "video", "perjalanan", "dokumenter", "video",
    "sejarah", "wisata"
)

fun aiSearchByKeywords(keywords: String): List<String> {
    val lowercaseKeywords = keywords.lowercase()

    val searchCriteria = mapOf(
        iconicViewKeywords to listOf("Landmark"),
        familyVacationKeywords to listOf("Beach", "Lake", "Natural", "Waterfall"),
        honeymoonKeywords to listOf("Beach"),
        educationCultureKeywords to listOf("Museum", "Historical"),
        photoVideoKeywords to listOf(
            "Beach",
            "Lake",
            "Natural",
            "Waterfall",
            "Landmark",
            "Historical",
            "Museum"
        ),
        recreationKeywords to listOf("Historical", "Museum"),
        adventureKeywords to listOf("Natural"),
        attractionKeywords to listOf("Beach", "Waterfall", "Lake")
    )

    val uniqueResults = searchCriteria.entries.filter { entry ->
        entry.key.any { keyword -> keyword in lowercaseKeywords }
    }.flatMap { it.value }.distinct()

    return uniqueResults
}

fun changeDateTimeGMT(tgl: String): String {
    val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))

    val date = inputFormat.parse(tgl)
    return outputFormat.format(date!!)
}

fun changeDateToSimply(tgl: String): String {
    val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))

    val date = inputFormat.parse(tgl)
    return outputFormat.format(date!!)
}

fun checkStatusTrip(dateString: String): Boolean {
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    val currentDate = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta")).time

    val date = dateFormat.parse(dateString)
    return currentDate.time >= date!!.time
}

fun changeDateToSlash(tgl: String): String {
    val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))

    val date = inputFormat.parse(tgl)
    return outputFormat.format(date!!)
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            ColorSecondary.copy(alpha = 0.6f),
            ColorSecondary.copy(alpha = 0.2f),
            ColorSecondary.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}




