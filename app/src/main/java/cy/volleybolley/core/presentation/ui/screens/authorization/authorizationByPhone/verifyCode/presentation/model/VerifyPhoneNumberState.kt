package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation.model

import cy.volleybolley.core.presentation.base.UiState

data class VerifyPhoneNumberState(
    val isLoading: Boolean = false,
    val code: String = "",
    val isBtnVerifyEnabled: Boolean = false,
    val remainingSendNewCodeTime: String = "",
    val isBtnNewCodeVisible: Boolean = true,
    val isBtnNewCodeEnabled: Boolean = false,
    val isCodeInputError: Boolean = false
) : UiState
