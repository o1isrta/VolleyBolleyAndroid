package cy.volleybolley.core.presentation.ui.model

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cy.volleybolley.R

object VolleyCustomTypography {

    // --- Шрифты ---
    val Actay = FontFamily(
        Font(R.font.actay_regular, FontWeight.Normal),
        Font(R.font.actay_wide_bold, FontWeight.Bold)
    )

    val Hero = FontFamily(
        Font(R.font.hero_regular, FontWeight.Normal),
        Font(R.font.hero_light, FontWeight.W200),
        Font(R.font.hero_bold, FontWeight.Bold)
    )

    // --- Заголовки ---
    val TitleXL = TextStyle(
        fontFamily = Actay,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 54.sp,
        letterSpacing = (-0.3).sp
    )

    val TitleXLAlt = TextStyle(
        fontFamily = Actay,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 54.sp,
        letterSpacing = 1.sp
    )

    val TitleLarge = TextStyle(
        fontFamily = Actay,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp
    )

    val TitleMedium = TextStyle(
        fontFamily = Actay,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )

    // --- Текст ---
    val BodyBoldMedium = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )

    val BodyBold = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val BodyRegular = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val BodySmall = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        letterSpacing = 0.sp
    )

    val BodyLight = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.W200,
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        letterSpacing = 0.sp
    )

    val BodyBoldSmall = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 14.4.sp,
        letterSpacing = 0.sp
    )

    val BodyTiny = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp
    )

    // --- Кнопки ---
    val ButtonText = TextStyle(
        fontFamily = Actay,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val ButtonSText = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val ButtonXSText = TextStyle(
        fontFamily = Hero,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    // --- Лого ---
    val LogoDisplay = TextStyle(
        fontFamily = Actay,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )
}
