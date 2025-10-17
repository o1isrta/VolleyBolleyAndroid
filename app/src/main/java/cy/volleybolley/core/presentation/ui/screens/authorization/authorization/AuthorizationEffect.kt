package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import cy.volleybolley.core.presentation.base.UiEffect

sealed class AuthorizationEffect : UiEffect {
    data class NavigateToRegistration(val user: String) : AuthorizationEffect()
    object LaunchGoogleSignIn : AuthorizationEffect()
    data class ShowError(val message: String) : AuthorizationEffect()
}
