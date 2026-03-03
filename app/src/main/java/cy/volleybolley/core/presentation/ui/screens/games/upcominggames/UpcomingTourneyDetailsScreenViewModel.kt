package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

class UpcomingTourneyDetailsScreenViewModel(
    private val tournamentDetails: TournamentDetails?
) : BaseViewModel<UpcomingTourneyDetailsScreenState, UpcomingTourneyDetailsScreenEvent, UpcomingTourneyDetailsScreenEffect>(
    initialState = UpcomingTourneyDetailsScreenState(tournamentDetails = tournamentDetails)
) {
    override val tag: String = "UpcomingTourneyDetailsScreenViewModel"

    override fun obtainEvent(event: UpcomingTourneyDetailsScreenEvent) {
        when (event) {
            is UpcomingTourneyDetailsScreenEvent.OnBackClicked -> {
                sendUiEffect(UpcomingTourneyDetailsScreenEffect.NavigateBack)
            }
            is UpcomingTourneyDetailsScreenEvent.OnViewPlayersClicked -> {
                tournamentDetails?.let {
                    sendUiEffect(UpcomingTourneyDetailsScreenEffect.NavigateToJoinedPlayers(it))
                }
            }
        }
    }
}
