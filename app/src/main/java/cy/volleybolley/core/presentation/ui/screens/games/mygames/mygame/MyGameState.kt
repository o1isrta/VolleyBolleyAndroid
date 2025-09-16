package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame

import cy.volleybolley.core.presentation.base.UiState

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

//State
data class MyGameState(
    val details: GameDetails = myGameDetailsStub()
) : UiState

// Стаб
fun myGameDetailsStub() = GameDetails(
    gameId = 1,
    gameType = "GAME",
    host = Host(id = 10, name = "Artem Ivanov", avatar = null, level = "L"),
    message = "Hi! Just old friends meet at the court, beer afterwards, come see us :)",
    courtLocation = Location(
        longitude = 98.2929,
        latitude = 7.8471,
        courtName = "Karon Beach Club",
        locationName = "Patak Rd, Mueang Phuket"
    ),
    startTime = "2025-10-10T18:00:00",
    endTime = "2025-10-10T20:00:00",
    gender = "Mix",
    levels = listOf("Light"),
    pricePerPerson = "2",
    maximumPlayers = 4,
    paymentType = "Thai bank",
    paymentAccount = "988 016 7890",
    currencyType = "$",
    players = listOf(
        PlayerShort("Artem Ivanov", "L"),
        PlayerShort("Aleksandr Abramov", "L")
    )
)
