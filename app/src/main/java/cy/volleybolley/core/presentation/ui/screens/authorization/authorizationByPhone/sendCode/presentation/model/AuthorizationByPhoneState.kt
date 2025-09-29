package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model

import cy.volleybolley.core.presentation.base.UiState

data class AuthorizationByPhoneState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val isPhoneNumberInputError: Boolean = false,
    val isBtnSendCodeEnabled: Boolean = false
) : UiState
