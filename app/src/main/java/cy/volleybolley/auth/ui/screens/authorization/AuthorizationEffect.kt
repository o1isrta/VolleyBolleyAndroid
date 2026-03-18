package cy.volleybolley.auth.ui.screens.authorization

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface AuthorizationEffect : UiEffect {
    class NavigateToRegistration(val user: String) : AuthorizationEffect
    object NavigateToHome : AuthorizationEffect
    class ShowToast(val message: String) : AuthorizationEffect
}
