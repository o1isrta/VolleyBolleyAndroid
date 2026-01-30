package cy.volleybolley.auth.phone.ui.presentation.model

import cy.volleybolley.auth.phone.domain.ResendCodeToken
import cy.volleybolley.core.presentation.base.UiState

data class AuthorizationByPhoneState(
    val isLoading: Boolean = false,

    val phoneNumber: String = "",
    val isPhoneNumberValid: Boolean = false,
    val isPhoneNumberInputError: Boolean = false,

    val code: String = "",
    val isCodeInputError: Boolean = false,

    val verificationId: String? = null,
    var resendToken: ResendCodeToken? = null,

    val step: Step = Step.ENTER_PHONE
) : UiState {
    enum class Step {
        ENTER_PHONE,
        VERIFY_CODE
    }
}
