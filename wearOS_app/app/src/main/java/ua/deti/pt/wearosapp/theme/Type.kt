package ua.deti.pt.wearosapp.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Typography
import ua.deti.pt.wearosapp.R

private val myCustomFont = FontFamily(
    Font(R.font.dmsans)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = myCustomFont,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    title1 = TextStyle(
        fontFamily = myCustomFont,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    caption1 = TextStyle(
        fontFamily = myCustomFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)