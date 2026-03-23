package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface GameHomeEffect : UiEffect {
    data object NavigateToMyGames : GameHomeEffect
    data object NavigateToUpcomingGames : GameHomeEffect
    data object NavigateToInvites : GameHomeEffect
    data object NavigateToArchive : GameHomeEffect
}
