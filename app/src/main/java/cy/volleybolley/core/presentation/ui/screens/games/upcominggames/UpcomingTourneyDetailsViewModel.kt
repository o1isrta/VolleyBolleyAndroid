package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

class UpcomingTourneyDetailsViewModel(
    private val tournamentDetails: TournamentDetails?
) : BaseViewModel<UpcomingTourneyDetailsState, UpcomingTourneyDetailsEvent, UpcomingTourneyDetailsEffect>(
    initialState = UpcomingTourneyDetailsState(tournamentDetails = tournamentDetails)
) {
    override fun obtainEvent(event: UpcomingTourneyDetailsEvent) {
        when (event) {
            is UpcomingTourneyDetailsEvent.OnBackClicked -> {
                sendUiEffect(UpcomingTourneyDetailsEffect.NavigateBack)
            }
            is UpcomingTourneyDetailsEvent.OnViewPlayersClicked -> {
                tournamentDetails?.let {
                    sendUiEffect(UpcomingTourneyDetailsEffect.NavigateToJoinedPlayers(it))
                }
            }
        }
    }
}
