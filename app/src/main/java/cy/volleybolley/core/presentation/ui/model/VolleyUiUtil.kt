package cy.volleybolley.core.presentation.ui.model

import androidx.compose.ui.unit.IntSize
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqString
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqStringType
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerActivityTemp
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerDetailTemp
import cy.volleybolley.profile.presentation.ui.screens.players.model.PlayerTemp
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType
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
//    const val NEXT_LINE = "\n"

    // Weight ratio for About Screen
    const val ABOUT_SCREEN_TITLES_WEIGHT = 0.37f
    const val ABOUT_SCREEN_CONTENT_WEIGHT = 0.63f

    // ChangePhotoScreen log tag
    const val PHOTO_FILE_TAG = "photo_tag"

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
        "Earn 10 points from higher-level players within the last 60 days. The higher the evaluator's level," +
        " the more weight their rating carries.\n" +
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

    val mockPlayers: List<PlayerTemp> = listOf(
        PlayerTemp(
            id = 1,
            firstName = "Иван",
            lastName = "Иванов",
            avatarUrl = null,
            isFavorite = true,
            level = "LIGHT"
        ),
        PlayerTemp(
            id = 2,
            firstName = "Анна",
            lastName = "Петрова",
            avatarUrl = null,
            isFavorite = false,
            level = "MEDIUM"
        ),
        PlayerTemp(
            id = 3,
            firstName = "Сергей",
            lastName = "Смирнов",
            avatarUrl = null,
            isFavorite = true,
            level = "HARD"
        ),
        PlayerTemp(
            id = 4,
            firstName = "Елена",
            lastName = "Васильева",
            avatarUrl = null,
            isFavorite = false,
            level = "PRO"
        ),
    )

    val mockPlayerDetails: List<PlayerDetailTemp> = listOf(
        PlayerDetailTemp(
            id = 1,
            firstName = "Иван",
            lastName = "Иванов",
            avatarUrl = null,
            isFavorite = true,
            level = "LIGHT",
            latestActivity = listOf()
        ),
        PlayerDetailTemp(
            id = 2,
            firstName = "Анна",
            lastName = "Петрова",
            avatarUrl = null,
            isFavorite = false,
            level = "MEDIUM",
            latestActivity = listOf(
                PlayerActivityTemp(
                    eventTimestamp = "2025-07-13T14:23:45Z",
                    courtLocation = Location(
                        longitude = 37.6173,
                        latitude = 55.7558,
                        courtName = "Спортивный комплекс",
                        locationName = "Красногорск"
                    )
                ),
            )
        ),
        PlayerDetailTemp(
            id = 3,
            firstName = "Сергей",
            lastName = "Смирнов",
            avatarUrl = null,
            isFavorite = true,
            level = "HARD",
            latestActivity = listOf(
                PlayerActivityTemp(
                    eventTimestamp = "2025-08-16T14:23:45Z",
                    courtLocation = Location(
                        longitude = 37.6156,
                        latitude = 55.7536,
                        courtName = "Зал №2",
                        locationName = "Химки"
                    )
                ),
                PlayerActivityTemp(
                    eventTimestamp = "2025-08-14T23:23:45Z",
                    courtLocation = Location(
                        longitude = 37.6194,
                        latitude = 55.7523,
                        courtName = "Арена Восток",
                        locationName = "Казань"
                    )
                ),
            )
        ),
        PlayerDetailTemp(
            id = 4,
            firstName = "Елена",
            lastName = "Васильева",
            avatarUrl = null,
            isFavorite = false,
            level = "PRO",
            latestActivity = listOf(
                PlayerActivityTemp(
                    eventTimestamp = "2025-08-16T14:23:45Z",
                    courtLocation = Location(
                        longitude = 37.6176,
                        latitude = 55.7538,
                        courtName = "Турнир Кубка",
                        locationName = "Новосибирск"
                    )
                ),
            )
        )
    )

    val mockPayments: List<Payment> = listOf(
        Payment(
            type = PaymentType.THAIBANK,
            account = "000 000 0000",
            isPreferred = false
        ),
        Payment(
            type = PaymentType.CASH,
            account = "",
            isPreferred = true
        ),
        Payment(
            type = PaymentType.REVOLUT,
            account = "",
            isPreferred = false
        )
    )

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
}
