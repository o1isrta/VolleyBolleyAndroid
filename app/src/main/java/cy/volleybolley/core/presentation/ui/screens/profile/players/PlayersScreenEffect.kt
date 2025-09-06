package cy.volleybolley.core.presentation.ui.screens.profile.players

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface PlayersScreenEffect : UiEffect {
    data class NavigateFromPlayersScreen(val route: NavMap?) : PlayersScreenEffect
}
