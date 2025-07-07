package cy.volleybolley.core.presentation.ui.model

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cy.volleybolley.R

object VolleyType {

    val HeroFontFamily = FontFamily(
        Font(R.font.hero_light_200, FontWeight.Light),
        Font(R.font.hero_medium_400, FontWeight.Medium),
        Font(R.font.hero_bold_700, FontWeight.Bold),
    )

    val TextStyleLight14 = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontFamily = HeroFontFamily,
        fontWeight = FontWeight.Light,
        letterSpacing = 0.sp,
    )

    val TextStyleMedium16 = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = HeroFontFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
    )

}