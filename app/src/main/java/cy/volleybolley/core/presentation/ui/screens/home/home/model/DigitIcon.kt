package cy.volleybolley.core.presentation.ui.screens.home.home.model

import androidx.annotation.DrawableRes
import cy.volleybolley.R

enum class DigitIcon(@DrawableRes val resId: Int) {
    ZERO(R.drawable.digit_0),
    ONE(R.drawable.digit_1),
    TWO(R.drawable.digit_2),
    THREE(R.drawable.digit_3),
    FOUR(R.drawable.digit_4),
    FIVE(R.drawable.digit_5),
    SIX(R.drawable.digit_6),
    SEVEN(R.drawable.digit_7),
    EIGHT(R.drawable.digit_8),
    NINE(R.drawable.digit_9);

    companion object {
        @JvmStatic
        fun getIconResByString(digitStringValue: String): Int {
            return when (digitStringValue) {
                "1" -> ONE.resId
                "2" -> TWO.resId
                "3" -> THREE.resId
                "4" -> FOUR.resId
                "5" -> FIVE.resId
                "6" -> SIX.resId
                "7" -> SEVEN.resId
                "8" -> EIGHT.resId
                "9" -> NINE.resId
                else -> ZERO.resId
            }
        }
    }
}
