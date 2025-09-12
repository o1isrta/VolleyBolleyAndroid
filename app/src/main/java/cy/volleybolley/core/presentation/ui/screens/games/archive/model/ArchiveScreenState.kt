package cy.volleybolley.core.presentation.ui.screens.games.archive.model

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Game
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Host
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.PlayerShort
import cy.volleybolley.courts.domain.model.Location
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.max

data class ArchiveScreenState(
    val emptyArchive: Boolean = false,
    val games: List<Game> = listOf(
        gameStub(1, "GAME"),
        gameStub(2, "TOURNAMENT"),
        gameStub(1, "GAME"),
        gameStub(2, "TOURNAMENT"),
    )
) : UiState

fun gameStub(id: Int, type: String) = Game(
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
        PlayerShort(101, "Artem Ivanov", "L"), PlayerShort(1, "Aleksandr Abramov", "L")
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
