package cy.volleybolley.auth.chooseMethod.model

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface AuthorizationEffect : UiEffect {
    object NavigateToRegistration : AuthorizationEffect
    object NavigateToHome : AuthorizationEffect
    class ShowToast(val message: String) : AuthorizationEffect
}
