package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import cy.volleybolley.core.presentation.base.UiEffect

sealed class RegistrationEffect : UiEffect {
    object NavigateToHome : RegistrationEffect()
}
