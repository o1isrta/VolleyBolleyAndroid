package cy.volleybolley.core.presentation.ui.model

import android.util.Log
import androidx.compose.ui.unit.IntSize
import cy.volleybolley.BuildConfig
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqString
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqStringType
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
    const val TIME_PATTERN_FOR_PARSE = "yyyy-MM-dd'T'HH:mm:ss"
    const val DATE_OF_ACTIVITY_PATTERN = "dd MMMM"
    const val MILLIS_IN_HOUR = 3_600_000L

    const val FAQ_BULLET_OUT_PREFIX = " • "

    // Weight ratio for About Screen
    const val ABOUT_SCREEN_TITLES_WEIGHT = 0.37f
    const val ABOUT_SCREEN_CONTENT_WEIGHT = 0.63f

    // ChangePhotoScreen log tag
    const val PHOTO_FILE_TAG = "photo_tag"

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
    fun parseTimeStringToActivityDateString(utcString: String, hoursOffset: Int): String {
        val formatter = SimpleDateFormat(TIME_PATTERN_FOR_PARSE, Locale.getDefault())
        val localDate = formatter.parse(utcString)
        return localDate?.let {
            it.time += hoursOffset * MILLIS_IN_HOUR
            val activityDateFormatter = SimpleDateFormat(DATE_OF_ACTIVITY_PATTERN, Locale.ENGLISH)
            activityDateFormatter.format(localDate).lowercase()
        } ?: ""
    }

    @JvmStatic
    fun parseMarkdown(faqMarkdown: String): List<FaqString> {
        return faqMarkdown.lines().map { string ->
            getFaqStringByPrefix(string)
        }
    }

    @JvmStatic
    private fun getFaqStringByPrefix(string: String): FaqString {
        return when {
            string.startsWith(FaqStringType.HEADER.prefix) -> {
                FaqString(
                    type = FaqStringType.HEADER,
                    value = string.drop(FaqStringType.HEADER.prefix.length),
                )
            }

            string.startsWith(FaqStringType.BULLET.prefix) -> {
                FaqString(
                    type = FaqStringType.BULLET,
                    value = string.drop(FaqStringType.BULLET.prefix.length),
                )
            }

            else -> {
                FaqString(
                    type = FaqStringType.REGULAR,
                    value = string,
                )
            }
        }
    }

    @JvmStatic
    fun showDebugLog(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    @JvmStatic
    fun showDebugExceptionLog(tag: String, message: String, e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message, e)
        }
    }
}
