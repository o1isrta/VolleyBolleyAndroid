package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

import cy.volleybolley.core.presentation.base.UiState
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.max

private const val TYPE_GAME = "GAME"
private const val TYPE_TOURNAMENT = "TOURNAMENT"
private const val HOST_NAME = "Artem Ivanov"
private const val LEVEL_L = "L"
private const val GENDER_MIX = "Mix"
private const val PAYMENT_TYPE_THAI_BANK = "Thai bank"
private const val CURRENCY_USD = "$"
private const val PAYMENT_ACCOUNT = "988 016 7890"

private val LEVELS_LIGHT = listOf("Light")

private val LONG_MESSAGE: String = """
    Hi! This is a really long test message to check how the bubble expands when there are many
    characters inside. It should properly wrap across multiple lines, no cuts.
""".trimIndent()

private const val SHORT_MESSAGE: String =
    "Afterlunch meet. 6$ entry fee, our favorite place, don’t miss"

data class GameDetails(
    val gameId: Int,
    val gameType: String,
    val host: Host,
    val message: String,
    val courtLocation: Location,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val pricePerPerson: String,
    val maximumPlayers: Int,
    val paymentType: String,
    val paymentAccount: String,
    val currencyType: String,
    val players: List<PlayerShort>,
)

data class Host(
    val id: Int,
    val name: String,
    val avatar: String?,
    val level: String,
)

data class Location(
    val longitude: Double,
    val latitude: Double,
    val courtName: String,
    val locationName: String,
)

data class PlayerShort(
    val name: String,
    val level: String?,
)

// State
data class MyGamesState(
    val hasGames: Boolean = true,
    val games: List<GameDetails> = listOf(
        gameStub(1, TYPE_GAME),
        gameStub(2, TYPE_TOURNAMENT),
    ),
) : UiState

// Stub
fun gameStub(
    id: Int,
    type: String,
): GameDetails {
    val message = if (id == 1) LONG_MESSAGE else SHORT_MESSAGE
    val courtName =
        if (id == 1) "Karon Beach Club" else "The Shore at Katathani Resort"
    val locationName = if (id == 1) "Karon" else "Kata Noi"
    val startTime = if (id == 1) "2025-10-10T18:00:00" else "2025-10-16T13:00:00"
    val endTime = if (id == 1) "2025-10-10T20:00:00" else "2025-10-16T14:00:00"

    return GameDetails(
        gameId = id,
        gameType = type,
        host = Host(
            id = 10,
            name = HOST_NAME,
            avatar = null,
            level = LEVEL_L,
        ),
        message = message,
        courtLocation = Location(
            longitude = 98.2929,
            latitude = 7.8471,
            courtName = courtName,
            locationName = locationName,
        ),
        startTime = startTime,
        endTime = endTime,
        gender = GENDER_MIX,
        levels = LEVELS_LIGHT,
        pricePerPerson = "2",
        maximumPlayers = 4,
        paymentType = PAYMENT_TYPE_THAI_BANK,
        paymentAccount = PAYMENT_ACCOUNT,
        currencyType = CURRENCY_USD,
        players = listOf(
            PlayerShort(
                name = HOST_NAME,
                level = LEVEL_L,
            ),
            PlayerShort(
                name = "Aleksandr Abramov",
                level = LEVEL_L,
            ),
        ),
    )
}

fun formatDateTimeRange(startIso: String, endIso: String): Pair<String, String> {
    val locale = Locale.ENGLISH
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale).apply {
        timeZone = java.util.TimeZone.getDefault()
    }
    val start = parser.parse(startIso)
    val end = parser.parse(endIso)
    val dateFmt = SimpleDateFormat("d MMMM", locale)
    val timeFmt = SimpleDateFormat("h:mm a", locale)
    val date = dateFmt.format(start ?: 0)
    val startTime = timeFmt.format(start ?: 0).lowercase(locale)
    val endTime = timeFmt.format(end ?: max(start?.time ?: 0, 0)).lowercase(locale)
    return date to "$startTime–$endTime"
}
