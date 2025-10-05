package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import cy.volleybolley.core.presentation.base.UiEffect

sealed class AuthorizationEffect : UiEffect {
    object NavigateToRegistration : AuthorizationEffect()
}
