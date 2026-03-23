package cy.volleybolley.registration.presentation.ui.screens.registration

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface RegistrationEffect : UiEffect {
    object NavigateToHome : RegistrationEffect
    class ShowToast(val message: String) : RegistrationEffect
}
