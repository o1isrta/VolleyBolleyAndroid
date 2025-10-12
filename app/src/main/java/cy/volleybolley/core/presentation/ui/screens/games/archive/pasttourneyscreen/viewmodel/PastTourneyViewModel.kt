package cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.TeamsRoute
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.effect.PastTourneyEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.event.PastTourneyEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.model.PastTourneyState

class PastTourneyViewModel :
    BaseViewModel<PastTourneyState, PastTourneyEvent, PastTourneyEffect>(
        initialState = PastTourneyState.Content()
    ) {
    override val tag: String = PastTourneyViewModel::class.simpleName ?: ""

    override fun obtainEvent(event: PastTourneyEvent) {
        when (event) {
            PastTourneyEvent.OnBackClick -> sendUiEffect(PastTourneyEffect.NavigateBack)
            is PastTourneyEvent.OnMapClick -> sendUiEffect(PastTourneyEffect.OpenMap(event.location))
            is PastTourneyEvent.OnJoinedPlayersClick -> sendUiEffect(PastTourneyEffect.Navigate(TeamsRoute))
            //
            PastTourneyEvent.Refresh -> {
                // Выполнить запрос данных по турниру
            }
        }
    }
}
