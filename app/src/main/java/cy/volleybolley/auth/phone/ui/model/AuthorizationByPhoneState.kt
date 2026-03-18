package cy.volleybolley.auth.phone.ui.model

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

    val isResendVisible: Boolean = false,
    val isResendEnabled: Boolean = false,
    val remainingResendTime: Int = 0,

    val step: Step = Step.ENTER_PHONE
) : UiState {
    enum class Step {
        ENTER_PHONE,
        VERIFY_CODE
    }
}
