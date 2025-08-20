package cy.volleybolley.core.presentation.ui.screens.profile.faq

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface FaqScreenEffect : UiEffect {
    data class NavigateFromFaqScreen(val route: NavMap?): FaqScreenEffect
}
