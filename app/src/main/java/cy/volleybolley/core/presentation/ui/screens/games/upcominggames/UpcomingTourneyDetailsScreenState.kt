package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

data class UpcomingTourneyDetailsScreenState(
    val isLoading: Boolean = false,
    val tournamentDetails: TournamentDetails? = null
) : UiState
