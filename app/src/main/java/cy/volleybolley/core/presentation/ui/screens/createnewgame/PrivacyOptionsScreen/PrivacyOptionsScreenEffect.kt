package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiEffect

sealed class PrivacyOptionsScreenEffect  : UiEffect {
    data class ShowError(val message: String) : PrivacyOptionsScreenEffect()
    object NavigateBack : PrivacyOptionsScreenEffect()
}
