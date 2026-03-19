package cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect.ArchiveEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect.ArchiveEffect.NavigateBack
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect.ArchiveEffect.NavigateToCreateGame
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect.ArchiveEffect.NavigateToPastGame
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect.ArchiveEffect.NavigateToPastTourney
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect.ArchiveEffect.OpenMap
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.event.ArchiveEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.model.ArchiveState
import cy.volleybolley.games.domain.model.event.EventType

class ArchiveViewModel :
    BaseViewModel<ArchiveState, ArchiveEvent, ArchiveEffect>(initialState = ArchiveState.Content()) {
    override fun obtainEvent(event: ArchiveEvent) {
        when (event) {
            ArchiveEvent.ClickBack -> sendUiEffect(NavigateBack)
            ArchiveEvent.ClickCreateGame -> sendUiEffect(NavigateToCreateGame)
            is ArchiveEvent.ClickDetails -> {
                val effect = when (event.competitionEvent.type) {
                    EventType.GAME -> NavigateToPastGame
                    EventType.TOURNAMENT -> NavigateToPastTourney
                }
                sendUiEffect(effect)
            }

            is ArchiveEvent.ClickMap -> sendUiEffect(OpenMap(event.location))
            ArchiveEvent.Refresh -> {
                // Выполнить запрос архива игр/турниров
            }
        }
    }
}
