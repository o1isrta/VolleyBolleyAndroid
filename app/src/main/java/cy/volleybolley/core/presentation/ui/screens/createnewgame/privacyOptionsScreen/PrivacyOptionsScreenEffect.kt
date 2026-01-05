package cy.volleybolley.core.presentation.ui.screens.createnewgame.privacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiEffect

sealed class PrivacyOptionsScreenEffect : UiEffect {
    data class ShowErrorMessage(val message: String) : PrivacyOptionsScreenEffect()
    data class ShowErrorMessageById(val messageId: Int) : PrivacyOptionsScreenEffect()
    object NavigateBack : PrivacyOptionsScreenEffect()
}
