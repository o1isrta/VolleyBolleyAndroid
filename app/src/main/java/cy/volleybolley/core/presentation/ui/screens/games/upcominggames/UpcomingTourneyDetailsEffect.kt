package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

sealed interface UpcomingTourneyDetailsEffect : UiEffect {
    data object NavigateBack : UpcomingTourneyDetailsEffect
    data class NavigateToJoinedPlayers(val tournamentDetails: TournamentDetails) : UpcomingTourneyDetailsEffect
}
