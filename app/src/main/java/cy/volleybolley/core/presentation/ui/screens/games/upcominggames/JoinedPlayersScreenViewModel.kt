package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

class JoinedPlayersScreenViewModel(
    tournamentDetails: TournamentDetails
) : BaseViewModel<JoinedPlayersScreenState, JoinedPlayersScreenEvent, JoinedPlayersScreenEffect>(
    initialState = JoinedPlayersScreenState(tournamentDetails = tournamentDetails)
) {
    override fun obtainEvent(event: JoinedPlayersScreenEvent) {
        when (event) {
            is JoinedPlayersScreenEvent.OnBackClicked -> {
                sendUiEffect(JoinedPlayersScreenEffect.NavigateBack)
            }
        }
    }
}
