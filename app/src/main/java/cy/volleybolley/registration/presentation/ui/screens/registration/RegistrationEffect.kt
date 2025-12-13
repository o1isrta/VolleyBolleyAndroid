package cy.volleybolley.registration.presentation.ui.screens.registration

import cy.volleybolley.core.presentation.base.UiEffect

sealed class RegistrationEffect : UiEffect {
    data object NavigateToHome : RegistrationEffect()
    data class ShowToast(val message: String) : RegistrationEffect()
}
