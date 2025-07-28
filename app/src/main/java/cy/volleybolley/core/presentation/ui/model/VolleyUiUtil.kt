package cy.volleybolley.core.presentation.ui.model

import androidx.compose.ui.unit.IntSize

object VolleyUiUtil {
    //Gradient container values
    const val GRADIENT_COLOR_STOP_MARK_1 = 0.0f
    const val GRADIENT_COLOR_STOP_MARK_2 = 0.65f
    const val GRADIENT_COLOR_STOP_MARK_3 = 1.0f
    const val GRADIENT_COLOR_STOP_VALUE_1 = 0.02f
    const val GRADIENT_COLOR_STOP_VALUE_2 = 0.2f
    const val GRADIENT_COLOR_STOP_VALUE_3 = 0.4f
    const val GRADIENT_BORDER_ALPHA = 0.05f

    @JvmStatic
    fun getLimitedText(symbolLimit: Int?, text: String): String {
        return if (symbolLimit == null) {
            text
        } else {
            val substringRange = 0 until symbolLimit
            if (text.length <= symbolLimit) text else text.substring(substringRange)
        }
    }

    @JvmStatic
    fun getGradientRadiusByContainerSize(containerSize: IntSize): Float {
        return if (containerSize == IntSize.Zero) 1f else maxOf(containerSize.width, containerSize.height).toFloat()
    }
}
