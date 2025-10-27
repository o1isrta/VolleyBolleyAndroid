package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.model

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.provideMockTourney
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

sealed interface PastTourneyState : UiState {
    data object Loading : PastTourneyState
    data class Content(
        val tourney: TournamentDetails = provideMockTourney(1)
    ) : PastTourneyState

    data object Error : PastTourneyState
}
