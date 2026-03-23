package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.effect.PastTourneyEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.effect.PastTourneyEffect.NavigateBack
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.effect.PastTourneyEffect.NavigateToTeams
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.effect.PastTourneyEffect.OpenMap
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.event.PastTourneyEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.model.PastTourneyState

class PastTourneyViewModel :
    BaseViewModel<PastTourneyState, PastTourneyEvent, PastTourneyEffect>(
        initialState = PastTourneyState.Content()
    ) {
    override fun obtainEvent(event: PastTourneyEvent) {
        when (event) {
            PastTourneyEvent.OnBackClick -> sendUiEffect(NavigateBack)
            is PastTourneyEvent.OnMapClick -> sendUiEffect(OpenMap(event.location))
            is PastTourneyEvent.OnJoinedPlayersClick -> sendUiEffect(NavigateToTeams)
            //
            PastTourneyEvent.Refresh -> {
                // Выполнить запрос данных по турниру
            }
        }
    }
}
