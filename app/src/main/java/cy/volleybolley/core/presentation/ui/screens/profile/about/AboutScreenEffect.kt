package cy.volleybolley.core.presentation.ui.screens.profile.about

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface AboutScreenEffect : UiEffect {
    data class NavigateFromAboutScreen(val route: NavMap?): AboutScreenEffect
}
