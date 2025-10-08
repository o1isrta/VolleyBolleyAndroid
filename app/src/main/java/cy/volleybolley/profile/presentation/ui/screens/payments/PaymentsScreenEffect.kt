package cy.volleybolley.profile.presentation.ui.screens.payments

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface PaymentsScreenEffect : UiEffect {
    data class NavigateFromPaymentsScreen(val route: NavMap?) : PaymentsScreenEffect
}
