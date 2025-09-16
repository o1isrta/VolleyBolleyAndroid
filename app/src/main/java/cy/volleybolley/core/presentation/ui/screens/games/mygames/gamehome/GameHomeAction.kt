package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface GameHomeAction : UiEvent {
    data object ClickMyGames : GameHomeAction
    data object ClickUpcomingGames : GameHomeAction
    data object ClickInvites : GameHomeAction
    data object ClickArchive : GameHomeAction

    // Заглушка
    data object Refresh : GameHomeAction
}
