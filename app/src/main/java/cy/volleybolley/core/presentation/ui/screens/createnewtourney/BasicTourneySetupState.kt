package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createNewGame.model.Gender
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import java.time.LocalDate

private const val DEFAULT_START_HOUR = 2
private const val DEFAULT_START_MINUTE = 0
private const val DEFAULT_FINISH_HOUR = 4
private const val DEFAULT_FINISH_MINUTE = 0

data class BasicTourneySetupState(
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
    val gender: Gender = Gender.Mix,
    val levels: Set<Level> = setOf(Level.Light, Level.Medium, Level.Hard),
    val tourneyType: TourneyType = TourneyType.SINGLE_ELIMINATION,
    val isLoading: Boolean = false
) : UiState

enum class TourneyType(val displayText: String) {
    SINGLE_ELIMINATION("Single elimination"),
    DOUBLE_ELIMINATION("Double elimination"),
    ROUND_ROBIN("Round robin")
}
