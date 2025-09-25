package cy.volleybolley.core.presentation.ui.screens.home.home

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

interface HomeScreenEffect : UiEffect {
    data class NavigateFromHomeScreen(val route: NavMap): HomeScreenEffect
}
