package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

import cy.volleybolley.core.presentation.base.UiState
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.max

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

data class Host(val id: Int, val name: String, val avatar: String?, val level: String)
data class Location(val longitude: Double, val latitude: Double, val courtName: String, val locationName: String)
data class PlayerShort(val name: String, val level: String?)

// State
data class MyGamesState(
    val hasGames: Boolean = true, val games: List<GameDetails> = listOf(
        gameStub(1, "GAME"), gameStub(2, "TOURNAMENT")
    )
) : UiState

// demo stub
fun gameStub(id: Int, type: String) = GameDetails(
    gameId = id,
    gameType = type,
    host = Host(id = 10, name = "Artem Ivanov", avatar = null, level = "L"),
    message = if (id == 1) "Hi! This is a really long test message to check how the bubble expands when there are many characters inside. It should properly wrap across multiple lines, no cuts."
    else "Afterlunch meet. 6$ entry fee, our favorite place, don’t miss",
    courtLocation = Location(
        longitude = 98.2929,
        latitude = 7.8471,
        courtName = if (id == 1) "Karon Beach Club" else "The Shore at Katathani Resort",
        locationName = if (id == 1) "Karon" else "Kata Noi"
    ),
    startTime = if (id == 1) "2025-10-10T18:00:00" else "2025-10-16T13:00:00",
    endTime = if (id == 1) "2025-10-10T20:00:00" else "2025-10-16T14:00:00",
    gender = "Mix",
    levels = listOf("Light"),
    pricePerPerson = "2",
    maximumPlayers = 4,
    paymentType = "Thai bank",
    paymentAccount = "988 016 7890",
    currencyType = "$",
    players = listOf(
        PlayerShort("Artem Ivanov", "L"), PlayerShort("Aleksandr Abramov", "L")
    )
)

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
