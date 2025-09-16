package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

import cy.volleybolley.core.presentation.base.UiState

private const val TYPE_TOURNAMENT = "TOURNAMENT"
private const val LEVEL_L = "L"
private const val CURRENCY_USD = "$"
private const val HOST_NAME = "Artem Ivanov"
private const val PLAYER_ALEKSANDR = "Aleksandr Abramov"
private const val COURT_NAME = "Karon Beach Club"
private const val LOCATION_NAME = "Patak Rd, Mueang Phuket"
private const val PAYMENT_TYPE_THAI_BANK = "Thai bank"
private const val MESSAGE =
    "Hi! Just old friends meet at the court, beer afterwards, come see us :)"
private const val GENDER_MIX = "Mix"

private val LEVELS_LIGHT = listOf("Light")

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

data class Team(
    val teamId: Int,
    val players: List<PlayerShort>,
)

data class ShortTeam(
    val teamId: Int,
    val players: List<Int>,
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

// Stub
private val stubTournamentDetails: TournamentDetails = TournamentDetails(
    tournamentId = 1,
    isIndividual = true,
    tournamentType = TYPE_TOURNAMENT,
    host = Host(
        id = 10,
        name = HOST_NAME,
        avatar = null,
        level = LEVEL_L,
    ),
    message = MESSAGE,
    courtLocation = Location(
        longitude = 98.2929,
        latitude = 7.8471,
        courtName = COURT_NAME,
        locationName = LOCATION_NAME,
    ),
    startTime = "2025-10-10T18:00:00",
    endTime = "2025-10-10T20:00:00",
    gender = GENDER_MIX,
    levels = LEVELS_LIGHT,
    maximumPlayers = 16,
    maximumTeams = 8,
    pricePerPerson = "2",
    paymentType = PAYMENT_TYPE_THAI_BANK,
    paymentAccount = "988 016 7890",
    currencyType = CURRENCY_USD,
    teams = listOf(
        Team(
            teamId = 1,
            players = listOf(
                PlayerShort(
                    name = HOST_NAME,
                    level = LEVEL_L,
                ),
                PlayerShort(
                    name = PLAYER_ALEKSANDR,
                    level = LEVEL_L,
                ),
            ),
        ),
    ),
)

fun myTourneyStub(): TournamentDetails = stubTournamentDetails

data class MyTourneyState(
    val details: TournamentDetails = myTourneyStub(),
) : UiState
