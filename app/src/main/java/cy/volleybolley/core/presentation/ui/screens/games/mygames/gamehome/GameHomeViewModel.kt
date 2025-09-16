package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.ArchiveRoute
import cy.volleybolley.core.presentation.ui.navigation.GameInvitesRoute
import cy.volleybolley.core.presentation.ui.navigation.MyGamesRoute
import cy.volleybolley.core.presentation.ui.navigation.UpcomingGamesRoute

class GameHomeViewModel :
    BaseViewModel<GameHomeState, GameHomeAction, GameHomeEffect>(
        initialState = GameHomeState()
    ) {

    override val tag: String = "GameHomeVM"

    override fun obtainEvent(event: GameHomeAction) {
        when (event) {
            GameHomeAction.ClickMyGames -> sendUiEffect(GameHomeEffect.Navigate(MyGamesRoute))
            GameHomeAction.ClickUpcomingGames -> sendUiEffect(GameHomeEffect.Navigate(UpcomingGamesRoute))
            GameHomeAction.ClickInvites -> sendUiEffect(GameHomeEffect.Navigate(GameInvitesRoute))
            GameHomeAction.ClickArchive -> sendUiEffect(GameHomeEffect.Navigate(ArchiveRoute))

            GameHomeAction.Refresh -> {
                // TODO: подтянуть данные из домейна и обновить uiState
            }
        }
    }
}
