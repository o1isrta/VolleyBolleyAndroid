package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.Gender
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import java.time.LocalDate

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
    val startTime: VolleyTimeStamp? = VolleyTimeStamp(2, 0, true),
    val finishTime: VolleyTimeStamp? = VolleyTimeStamp(4, 0, true),
    val gender: Gender = Gender.Mix,
    val levels: Set<Level> = setOf(Level.Light, Level.Medium, Level.Hard),
    val isLoading: Boolean = false
) : UiState

