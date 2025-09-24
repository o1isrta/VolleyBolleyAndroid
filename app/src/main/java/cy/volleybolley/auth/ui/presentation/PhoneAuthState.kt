package cy.volleybolley.auth.ui.presentation

import androidx.annotation.StringRes
import com.google.firebase.auth.PhoneAuthProvider
import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.base.UiState

data class PhoneAuthState(
    val phoneNumber: String = "",
    val code: String = "",
    val verificationId: String? = null,
    val resendToken: PhoneAuthProvider.ForceResendingToken? = null,
    val isCodeSent: Boolean = false,
    val isLoading: Boolean = false,
    val resendTimeout: Int = 0,
    val isAuthorized: Boolean = false
) : UiState

sealed interface PhoneAuthEvent : UiEvent {
    data class SendCode(val phone: String) : PhoneAuthEvent
    object ResendCode : PhoneAuthEvent
    data class VerifyCode(val code: String) : PhoneAuthEvent
}

sealed interface PhoneAuthEffect : UiEffect {

    data class RequestPhoneVerification(
        val phone: String,
        val resendToken: PhoneAuthProvider.ForceResendingToken? = null
    ) : PhoneAuthEffect

    data class PhoneAuth(val idToken: String) : PhoneAuthEffect
    data class ShowError(@StringRes val messageRes: Int) : PhoneAuthEffect
}
