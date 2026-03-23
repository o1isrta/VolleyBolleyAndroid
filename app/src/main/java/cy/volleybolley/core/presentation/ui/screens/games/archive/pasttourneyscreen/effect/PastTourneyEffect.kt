package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.effect

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.courts.domain.model.Location

sealed interface PastTourneyEffect : UiEffect {
    data object NavigateBack : PastTourneyEffect
    data object NavigateToTeams : PastTourneyEffect
    data class OpenMap(val location: Location) : PastTourneyEffect
}
