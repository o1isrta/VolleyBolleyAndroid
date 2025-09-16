package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

import cy.volleybolley.core.presentation.base.UiState

data class TournamentDetails(
    val tournamentId: Int,
    val isIndividual: Boolean,
    val tournamentType: String,
    val host: Host,
    val message: String,
    val courtLocation: Location,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val maximumPlayers: Int,
    val maximumTeams: Int,
    val pricePerPerson: String,
    val paymentType: String,
    val paymentAccount: String?,
    val currencyType: String,
    val teams: List<Team>,
)

data class Team(val teamId: Int, val players: List<PlayerShort>)
data class ShortTeam(val teamId: Int, val players: List<Int>)

data class Host(val id: Int, val name: String, val avatar: String?, val level: String)
data class Location(
    val longitude: Double, val latitude: Double, val courtName: String, val locationName: String
)

data class PlayerShort(val name: String, val level: String?)

//Заглушка
fun myTourneyStub(): TournamentDetails = TournamentDetails(
    tournamentId = 1,
    isIndividual = true,
    tournamentType = "TOURNAMENT",
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
    maximumPlayers = 16,
    maximumTeams = 8,
    pricePerPerson = "2",
    paymentType = "Thai bank",
    paymentAccount = "988 016 7890",
    currencyType = "$",
    teams = listOf(
        Team(
            teamId = 1, players = listOf(
                PlayerShort("Artem Ivanov", "L"), PlayerShort("Aleksandr Abramov", "L")
            )
        )
    )
)

//State
data class MyTourneyState(
    val details: TournamentDetails = myTourneyStub()
) : UiState
