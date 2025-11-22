package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import cy.volleybolley.core.presentation.base.UiEffect

sealed class RegistrationEffect : UiEffect {
    data object NavigateToHome : RegistrationEffect()
    data class ShowToast(val message: String) : RegistrationEffect()
}
