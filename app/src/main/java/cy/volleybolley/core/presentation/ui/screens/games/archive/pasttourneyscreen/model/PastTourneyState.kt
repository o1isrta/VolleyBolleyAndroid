package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.model

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Host
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.PlayerShort
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.ShortTeam
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Tourney
import cy.volleybolley.courts.domain.model.Location

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

sealed interface PastTourneyState : UiState {
    data object Loading : PastTourneyState
    data class Content(val tourney: Tourney = mockTourney) : PastTourneyState
    data object Error : PastTourneyState
}

private val mockTourney: Tourney = Tourney(
    tournamentId = 1,
    isIndividual = false,
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
        ShortTeam(
            id = 1,
            players = listOf(
                PlayerShort(
                    playerId = 0,
                    name = HOST_NAME,
                    level = LEVEL_L,
                ),
                PlayerShort(
                    playerId = 1,
                    name = PLAYER_ALEKSANDR,
                    level = LEVEL_L,
                ),
            ),
        ),
    ),
)
