package cy.volleybolley.core.presentation.ui.model

import androidx.compose.ui.unit.IntSize
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
}
