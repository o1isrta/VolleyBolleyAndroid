package cy.volleybolley.core.presentation.ui.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cy.volleybolley.R

object VolleyTypography {

    // --- Шрифты ---
    val ActayFamily = FontFamily(
        Font(R.font.actay_regular, FontWeight.Normal),
        Font(R.font.actay_wide_bold, FontWeight.Bold)
    )

    val HeroFamily = FontFamily(
        Font(R.font.hero_regular, FontWeight.Normal),
        Font(R.font.hero_light, FontWeight.W200),
        Font(R.font.hero_bold, FontWeight.Bold)
    )

    // --- Заголовки ---
    val TitleXL = TextStyle(
        fontFamily = ActayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 54.sp,
        letterSpacing = (-0.3).sp
    )

    val TitleXLAlt = TextStyle(
        fontFamily = ActayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 54.sp,
        letterSpacing = 1.sp
    )

    val TitleLarge = TextStyle(
        fontFamily = ActayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp
    )

    val TitleMedium = TextStyle(
        fontFamily = ActayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )

    // --- Текст ---
    val BodyBoldMedium = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )

    val BodyBold = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val BodyBoldGradient = TextStyle(
        brush = Brush.verticalGradient(
            colors = listOf(
                VolleyColor.YellowForGradient,
                VolleyColor.GreenForGradient
            )
        ),
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val BodyRegular = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val BodySmall = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        letterSpacing = 0.sp
    )

    val BodyLight = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.W200,
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        letterSpacing = 0.sp
    )

    val BodyBoldSmall = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 14.4.sp,
        letterSpacing = 0.sp
    )

    val BodyTiny = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp
    )

    val BodyTinyBottomNavWhite = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp,
        color = VolleyColor.White
    )

    val BodyTinyBottomNavGradient = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp,
        brush = Brush.verticalGradient(
            colors = listOf(
                VolleyColor.YellowForGradient,
                VolleyColor.GreenForGradient
            )
        )
    )

    // --- Кнопки ---
    val ButtonText = TextStyle(
        fontFamily = ActayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val ButtonSText = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    val ButtonXSText = TextStyle(
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.2.sp,
        letterSpacing = 0.sp
    )

    // --- Лого ---
    val LogoDisplay = TextStyle(
        fontFamily = ActayFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )

    // --- Поля ввода ---
    val GradientFieldMedium = TextStyle(
        color = VolleyColor.TextField,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
    )

    val GradientFieldLight = TextStyle(
        color = VolleyColor.TextField,
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        fontFamily = HeroFamily,
        fontWeight = FontWeight.W200,
        letterSpacing = 0.sp,
    )

    val GradientFieldAlert = TextStyle(
        color = VolleyColor.White,
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        fontFamily = HeroFamily,
        fontWeight = FontWeight.W200,
        letterSpacing = 0.sp,
    )

    val CodeField = TextStyle(
        color = VolleyColor.TextField,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = HeroFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 10.sp,
        textAlign = TextAlign.Center
    )
}
