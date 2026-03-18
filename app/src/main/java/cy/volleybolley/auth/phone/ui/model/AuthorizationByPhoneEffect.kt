package cy.volleybolley.auth.phone.ui.model

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface AuthorizationByPhoneEffect : UiEffect {
    object NavigateToRegistration : AuthorizationByPhoneEffect
    object NavigateHome : AuthorizationByPhoneEffect
    data class ShowError(val message: String) : AuthorizationByPhoneEffect
}
