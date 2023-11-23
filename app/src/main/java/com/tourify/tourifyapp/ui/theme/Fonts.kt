package com.tourify.tourifyapp.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.tourify.tourifyapp.R

val fonts = FontFamily(
    Font(R.font.extrabold, FontWeight.ExtraBold),
    Font(R.font.bold, FontWeight.Bold),
    Font(R.font.semibold, FontWeight.SemiBold),
    Font(R.font.medium, FontWeight.Medium),
    Font(R.font.regular, FontWeight.Normal),
    Font(R.font.light, FontWeight.Light),
    Font(R.font.extralight, FontWeight.ExtraLight),
    Font(R.font.italic, FontWeight.Normal, FontStyle.Italic)
)