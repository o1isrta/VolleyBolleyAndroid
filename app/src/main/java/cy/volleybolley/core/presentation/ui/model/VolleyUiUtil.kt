package cy.volleybolley.core.presentation.ui.model

import androidx.compose.ui.unit.IntSize
import cy.volleybolley.core.presentation.ui.screens.profile.faq.model.FaqString
import cy.volleybolley.core.presentation.ui.screens.profile.faq.model.FaqStringType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object VolleyUiUtil {
    // Gradient container values
    const val GRADIENT_COLOR_STOP_MARK_1 = 0.0f
    const val GRADIENT_COLOR_STOP_MARK_2 = 0.65f
    const val GRADIENT_COLOR_STOP_MARK_3 = 1.0f
    const val GRADIENT_COLOR_STOP_VALUE_1 = 0.02f
    const val GRADIENT_COLOR_STOP_VALUE_2 = 0.2f
    const val GRADIENT_COLOR_STOP_VALUE_3 = 0.4f
    const val GRADIENT_BORDER_ALPHA = 0.05f

    const val DATE_FIELD_HINT = "__ /__ /____"
    const val DATE_OF_BIRTH_FIELD_PATTERN = "dd/MM/yyyy"
    const val DATE_OF_BIRTH_PATTERN_FOR_SERVER = "yyyy-MM-dd"

    const val FAQ_BULLET_OUT_PREFIX = " • "
    const val NEXT_LINE = "\n"
    // temp mock value
    const val MOCK_FAQ = "# Registration\n" +
        "To find the right games and teammates, choose your current skill level:\n" +
        "Options:\n" +
        "- Light (L1-L3) - Beginner\n" +
        "- Medium (M1-M3) - Confident amateur\n" +
        "- Hard (H1-H3) - Advanced\n" +
        "- Pro (P1-P3) - Professional\n" +
        "The higher the number, the higher the skill. You may change later based on player ratings.\n" +
        "# Post-Game Ratings\n" +
        "After each game, teammates can rate your level.\n" +
        "Once you receive 6 ratings, your level may change:\n" +
        "- 5+ positive ratings — promoted one step\n" +
        "- 5+ negative ratings — demoted one step\n" +
        "- Mixed feedback — level stays the same\n" +
        "# Want to move to the next category?\n" +
        "Earn 10 points from higher-level players within the last 60 days. The higher the evaluator's level, the more weight their rating carries.\n" +
        "# Levels can drop due to:\n" +
        "Ratings (6 within 60 days, 5+ downs = demotion)\n" +
        "Inactivity:\n" +
        "- 90 days = minus 1 step\n" +
        "- 180 days = reset to lowest level in current category\n" +
        "# Fair Play Policy\n" +
        "We ensure a fair system:\n" +
        "- Ratings are anonymous\n" +
        "- Max 2 ratings from the same player in 60 days\n" +
        "- Light has minimal impact on Hard and Pro levels\n" +
        "- Pro levels are harder to reach\n" +
        "Play fair — your level will speak for itself."

    @JvmStatic
    fun getLimitedText(symbolLimit: Int?, text: String): String {
        return if (symbolLimit == null) {
            text
        } else {
            if (text.length <= symbolLimit) text else text.take(symbolLimit)
        }
    }

    @JvmStatic
    fun getGradientRadiusByContainerSize(containerSize: IntSize): Float {
        return if (containerSize == IntSize.Zero) 1f else maxOf(containerSize.width, containerSize.height).toFloat()
    }

    @JvmStatic
    fun convertMillisToTextDate(stringPattern: String, millis: Long): String {
        val formatter = SimpleDateFormat(stringPattern, Locale.getDefault())
        return formatter.format(Date(millis))
    }

    @JvmStatic
    fun convertTextDateToMillis(stringPattern: String, dateText: String): Long {
        val formatter = SimpleDateFormat(stringPattern, Locale.getDefault())
        val date = formatter.parse(dateText)
        return date?.time ?: 0L
    }

    @JvmStatic
    fun parseMarkdown(faqMarkdown: String): List<FaqString> {
        return faqMarkdown.split(NEXT_LINE).mapIndexed { index, string ->
            getFaqStringByPrefix(index, string)
        }
    }

    @JvmStatic
    private fun getFaqStringByPrefix(index: Int, string: String): FaqString {
        return when {
            string.startsWith(FaqStringType.HEADER.prefix) -> {
                FaqString(
                    index = index,
                    type = FaqStringType.HEADER,
                    value = string.drop(FaqStringType.HEADER.prefix.length),
                )
            }

            string.startsWith(FaqStringType.BULLET.prefix) -> {
                FaqString(
                    index = index,
                    type = FaqStringType.BULLET,
                    value = string.drop(FaqStringType.BULLET.prefix.length),
                )
            }

            else -> {
                FaqString(
                    index = index,
                    type = FaqStringType.REGULAR,
                    value = string,
                )
            }
        }
    }
}
