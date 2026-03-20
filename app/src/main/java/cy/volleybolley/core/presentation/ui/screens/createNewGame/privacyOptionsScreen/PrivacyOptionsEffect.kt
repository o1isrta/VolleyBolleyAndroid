package cy.volleybolley.core.presentation.ui.screens.createNewGame.privacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface PrivacyOptionsEffect : UiEffect {
    data class ShowErrorMessage(val message: String) : PrivacyOptionsEffect
    data class ShowErrorMessageById(val messageId: Int) : PrivacyOptionsEffect
    data object NavigateBack : PrivacyOptionsEffect
}
