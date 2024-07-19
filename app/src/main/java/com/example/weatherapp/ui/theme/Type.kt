package com.example.weatherapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

val font_basic = FontFamily(
    listOf(
        Font(R.font.font_basic)
    )
)

val font_unt = FontFamily(
    listOf(
        Font(R.font.font_unt)
    )
)

val monsterrat = FontFamily(
    listOf(
        Font(R.font.monsterrat)
    )
)

val monsterrat_bold = FontFamily(
    listOf(
        Font(R.font.monsterrat_bold)
    )
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = font_unt,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)