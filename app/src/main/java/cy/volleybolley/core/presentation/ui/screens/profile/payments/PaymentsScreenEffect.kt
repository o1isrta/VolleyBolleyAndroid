package cy.volleybolley.core.presentation.ui.screens.profile.payments

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface PaymentsScreenEffect : UiEffect {
    data class NavigateFromPaymentsScreen(val route: NavMap?) : PaymentsScreenEffect
}
