package cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.BasicGameSetupRoute
import cy.volleybolley.core.presentation.ui.navigation.PastGameRoute
import cy.volleybolley.core.presentation.ui.navigation.PastTourneyRoute
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.effect.ArchiveEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.event.ArchiveEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.model.ArchiveState
import cy.volleybolley.games.domain.model.event.EventType

class ArchiveViewModel :
    BaseViewModel<ArchiveState, ArchiveEvent, ArchiveEffect>(initialState = ArchiveState.Content()) {
    override val tag: String = ArchiveViewModel::class.simpleName ?: ""

    override fun obtainEvent(event: ArchiveEvent) {
        when (event) {
            ArchiveEvent.ClickBack -> sendUiEffect(ArchiveEffect.NavigateBack)
            ArchiveEvent.ClickCreateGame -> sendUiEffect(ArchiveEffect.Navigate(BasicGameSetupRoute))
            is ArchiveEvent.ClickDetails -> {
                val route = when (event.competitionEvent.type) {
                    EventType.GAME -> PastGameRoute
                    EventType.TOURNAMENT -> PastTourneyRoute
                }
                sendUiEffect(ArchiveEffect.Navigate(route))
            }

            is ArchiveEvent.ClickMap -> sendUiEffect(ArchiveEffect.OpenMap(event.location))
            ArchiveEvent.Refresh -> {
                // Выполнить запрос архива игр/турниров
            }
        }
    }
}
