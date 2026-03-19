package cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.effect.PastGameEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.event.PastGameEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.model.PastGameState

class PastGameViewModel : BaseViewModel<PastGameState, PastGameEvent, PastGameEffect>(
    initialState = PastGameState.Content()
) {
    override fun obtainEvent(event: PastGameEvent) {
        when (event) {
            PastGameEvent.OnBackClick -> sendUiEffect(PastGameEffect.NavigateBack)
            is PastGameEvent.OnMapClick -> sendUiEffect(PastGameEffect.OpenMap(event.location))
            PastGameEvent.Refresh -> {
                // Выполнить запрос данных по игре
            }
        }
    }

}
