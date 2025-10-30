package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.profile.domain.model.PersonalData

sealed class AuthorizationEffect : UiEffect {
    data class NavigateToRegistration(val personalData: PersonalData) : AuthorizationEffect()
    data object LaunchGoogleSignIn : AuthorizationEffect()
    data class ShowToast(val message: String) : AuthorizationEffect()
}
