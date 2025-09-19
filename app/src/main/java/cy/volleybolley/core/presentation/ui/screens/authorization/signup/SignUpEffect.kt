package cy.volleybolley.core.presentation.ui.screens.authorization.signup

import cy.volleybolley.core.presentation.base.UiEffect

sealed class SignUpEffect : UiEffect {
    object NavigateToRegistrationByPhone : SignUpEffect()
    object NavigateToRegistration : SignUpEffect()
}
