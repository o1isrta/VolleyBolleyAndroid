package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.basicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model.GameGender
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import java.time.LocalDate

private const val DEFAULT_START_HOUR = 2
private const val DEFAULT_START_MINUTE = 0
private const val DEFAULT_FINISH_HOUR = 4
private const val DEFAULT_FINISH_MINUTE = 0

data class BasicGameSetupScreenState(
    val message: String = "",
    val placeCourt: Court = Court(
        courtId = 1,
        price = "1$",
        description = "Karon Beach Club: Patak Rd, Mueang Phuket",
        location = Location(
            longitude = 55.0,
            latitude = 56.0,
            courtName = "Karon Beach Club",
            locationName = "Patak Rd, Mueang Phuket"
        ),
        contacts = listOf(),
        photo = "",
        tags = listOf()
    ),
    val date: LocalDate = LocalDate.now(),
    val startTime: VolleyTimeStamp? = VolleyTimeStamp(DEFAULT_START_HOUR, DEFAULT_START_MINUTE, true),
    val finishTime: VolleyTimeStamp? = VolleyTimeStamp(DEFAULT_FINISH_HOUR, DEFAULT_FINISH_MINUTE, true),
    val gender: GameGender = GameGender.Mix,
    val levels: Set<Level> = setOf(Level.Light, Level.Medium, Level.Hard),
    val showCalendar: Boolean = false,
    val isLoading: Boolean = false
) : UiState
