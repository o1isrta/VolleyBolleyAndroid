package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome.GameHomeEffect.NavigateToArchive
import cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome.GameHomeEffect.NavigateToInvites
import cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome.GameHomeEffect.NavigateToMyGames
import cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome.GameHomeEffect.NavigateToUpcomingGames

class GameHomeViewModel : BaseViewModel<GameHomeState, GameHomeAction, GameHomeEffect>(
    initialState = GameHomeState()
) {
    override fun obtainEvent(event: GameHomeAction) {
        when (event) {
            GameHomeAction.ClickMyGames -> sendUiEffect(NavigateToMyGames)
            GameHomeAction.ClickUpcomingGames -> sendUiEffect(NavigateToUpcomingGames)
            GameHomeAction.ClickInvites -> sendUiEffect(NavigateToInvites)
            GameHomeAction.ClickArchive -> sendUiEffect(NavigateToArchive)

            GameHomeAction.Refresh -> {
                // Подтянуть данные из домейна и обновить uiState
            }
        }
    }
}
