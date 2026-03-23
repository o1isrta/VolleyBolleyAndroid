package cy.volleybolley.auth.phone.ui

import androidx.lifecycle.viewModelScope
import cy.volleybolley.auth.phone.domain.CodeSentResult
import cy.volleybolley.auth.phone.domain.PhoneAuthError
import cy.volleybolley.auth.phone.domain.PhoneAuthService
import cy.volleybolley.auth.phone.domain.PhoneTokenAuthUseCase
import cy.volleybolley.auth.phone.domain.PhoneValidator
import cy.volleybolley.auth.phone.ui.model.AuthorizationByPhoneEffect
import cy.volleybolley.auth.phone.ui.model.AuthorizationByPhoneEvent
import cy.volleybolley.auth.phone.ui.model.AuthorizationByPhoneState
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.util.VolleyLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthorizationByPhoneViewModel(
    private val phoneAuthService: PhoneAuthService,
    private val phoneTokenAuthUseCase: PhoneTokenAuthUseCase
) : BaseViewModel<AuthorizationByPhoneState, AuthorizationByPhoneEvent, AuthorizationByPhoneEffect>(
    initialState = AuthorizationByPhoneState()
) {
    private var resendTimerJob: Job? = null

    override fun obtainEvent(event: AuthorizationByPhoneEvent) {
        when (event) {
            is AuthorizationByPhoneEvent.TypePhone -> {
                val isValid = PhoneValidator.isValidPhoneNumber(event.phone)
                uiStateMutable.update {
                    it.copy(
                        phoneNumber = event.phone,
                        isPhoneNumberValid = isValid,
                        isPhoneNumberInputError = event.phone.isNotEmpty() && !isValid
                    )
                }
            }

            is AuthorizationByPhoneEvent.TypeCode -> {
                uiStateMutable.update {
                    it.copy(
                        code = event.code,
                        isCodeInputError = false
                    )
                }
            }

            AuthorizationByPhoneEvent.SendCodeClicked -> sendCode()
            AuthorizationByPhoneEvent.ResendCodeClicked -> resendCode()
            AuthorizationByPhoneEvent.VerifyCodeClicked -> verifyCode()
        }
    }

    private fun sendCode() {
        val state = uiState.value
        if (!state.isPhoneNumberValid) {
            VolleyLog.w(tag, "sendCode() called but phone number is not valid")
            return
        }

        launchSafe(
            block = {
                VolleyLog.d(tag, "sendCode() starting")
                uiStateMutable.update { it.copy(isLoading = true) }

                val phone = normalizePhone(state.phoneNumber)
                VolleyLog.d(tag, "sendCode() normalized phone=$phone")
                val result = phoneAuthService.sendCode(phone, null)

                uiStateMutable.update { it.copy(isLoading = false) }

                result
                    .onSuccess { codeSentResult ->
                        VolleyLog.i(tag, "sendCode() SUCCESS, verificationId=${codeSentResult.verificationId}")
                        handleCodeSent(codeSentResult)
                    }
                    .onFailure { error ->
                        VolleyLog.e(tag, "sendCode() FAILED with error=$error")
                        handleSendCodeError(error)
                    }
            },
            onError = { error ->
                VolleyLog.e(tag, "sendCode() UNEXPECTED ERROR: ${error.message}", error)
                uiStateMutable.update { it.copy(isLoading = false) }
                uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError(UNEXPECTED_ERROR))
            },
            getErrorLogMessage = { "SendCode: unexpected error -> ${it.message}" }
        )
    }

    private fun resendCode() {
        val state = uiState.value
        val token = state.resendToken
        if (token == null) {
            VolleyLog.w(tag, "resendCode() called but resendToken is null")
            return
        }

        launchSafe(
            block = {
                VolleyLog.d(tag, "resendCode() starting")
                uiStateMutable.update {
                    it.copy(
                        isResendEnabled = false,
                        remainingResendTime = RESEND_TIMEOUT_SECONDS
                    )
                }

                startResendTimer()

                val phone = normalizePhone(state.phoneNumber)
                VolleyLog.d(tag, "resendCode() normalized phone=$phone")
                val result = phoneAuthService.sendCode(phone, token)

                result
                    .onSuccess { codeSentResult ->
                        VolleyLog.i(tag, "resendCode() SUCCESS, verificationId=${codeSentResult.verificationId}")
                        handleCodeSent(codeSentResult)
                    }
                    .onFailure { error ->
                        VolleyLog.e(tag, "resendCode() FAILED with error=$error")
                        handleSendCodeError(error)
                    }
            },
            onError = { error ->
                VolleyLog.e(tag, "resendCode() UNEXPECTED ERROR: ${error.message}", error)
                uiStateMutable.update { it.copy(isLoading = false, isResendEnabled = true) }
                uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError(UNEXPECTED_ERROR))
            },
            getErrorLogMessage = { "ResendCode: unexpected error -> ${it.message}" }
        )
    }

    private fun verifyCode() {
        val state = uiState.value
        val verificationId = state.verificationId
        if (verificationId == null) {
            VolleyLog.w(tag, "verifyCode() called but verificationId is null")
            return
        }

        if (state.code.length < SMS_CODE_LENGTH) {
            VolleyLog.d(tag, "verifyCode() code too short: ${state.code.length}")
            uiStateMutable.update { it.copy(isCodeInputError = true) }
            return
        }

        launchSafe(
            block = {
                VolleyLog.d(tag, "verifyCode() starting")
                uiStateMutable.update { it.copy(isLoading = true) }
                phoneAuthService.verifyCode(verificationId, state.code)
                    .onSuccess { idToken ->
                        VolleyLog.i(tag, "verifyCode() SUCCESS, token length=${idToken.length}")
                        onAuthorized(idToken)
                    }
                    .onFailure { error ->
                        VolleyLog.e(tag, "verifyCode() FAILED with error=$error")
                        handleVerifyCodeError(error)
                    }
            },
            onError = { error ->
                VolleyLog.e(tag, "verifyCode() UNEXPECTED ERROR: ${error.message}", error)
                uiStateMutable.update { it.copy(isLoading = false) }
                uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError(UNEXPECTED_ERROR))
            },
            getErrorLogMessage = { "VerifyCode: unexpected error -> ${it.message}" }
        )
    }

    private fun onAuthorized(idToken: String) {
        launchSafe(
            block = {
                VolleyLog.d(tag, "onAuthorized() calling loginWithPhone")
                uiStateMutable.update { it.copy(isLoading = true) }

                val result = phoneTokenAuthUseCase.loginWithPhone(idToken)

                uiStateMutable.update { it.copy(isLoading = false) }

                result
                    .onSuccess { authResult ->
                        VolleyLog.i(tag, "onAuthorized() SUCCESS, isRegistered=${authResult.isRegistered}")
                        if (authResult.isRegistered) {
                            uiEffectMutable.trySend(AuthorizationByPhoneEffect.NavigateHome)
                        } else {
                            uiEffectMutable.trySend(AuthorizationByPhoneEffect.NavigateToRegistration)
                        }
                    }
                    .onFailure {
                        VolleyLog.e(tag, "onAuthorized() loginWithPhone FAILED")
                        uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError("Authorization error"))
                    }
            },
            onError = { error ->
                VolleyLog.e(tag, "onAuthorized() UNEXPECTED ERROR: ${error.message}", error)
                uiStateMutable.update { it.copy(isLoading = false) }
                uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError("Unexpected error during authorization"))
            },
            getErrorLogMessage = { "PhoneTokenReceived: unexpected error -> ${it.message}" }
        )
    }

    private fun handleCodeSent(result: CodeSentResult) {
        VolleyLog.d(tag, "handleCodeSent() transitioning to VERIFY_CODE step")
        uiStateMutable.update {
            it.copy(
                isLoading = false,
                verificationId = result.verificationId,
                resendToken = result.resendToken,
                step = AuthorizationByPhoneState.Step.VERIFY_CODE,
                isResendVisible = true,
                isResendEnabled = false,
                remainingResendTime = RESEND_TIMEOUT_SECONDS,
                isCodeInputError = false
            )
        }
        startResendTimer()
    }

    private fun handleSendCodeError(error: PhoneAuthError) {
        VolleyLog.d(tag, "handleSendCodeError() error=$error")
        val message = when (error) {
            PhoneAuthError.INVALID_PHONE_NUMBER -> "Invalid phone number"
            PhoneAuthError.TOO_MANY_REQUESTS -> "Too many requests. Try again later"
            PhoneAuthError.SMS_QUOTA_EXCEEDED -> "SMS quota exceeded. Try again later"
            PhoneAuthError.NETWORK_ERROR -> "Network error. Check your connection"
            else -> "Failed to send code"
        }
        VolleyLog.d(tag, "handleSendCodeError() showing message: $message")
        uiStateMutable.update { it.copy(isLoading = false) }
        uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError(message))
    }

    private fun handleVerifyCodeError(error: PhoneAuthError) {
        VolleyLog.d(tag, "handleVerifyCodeError() error=$error")
        val message = when (error) {
            PhoneAuthError.INVALID_VERIFICATION_CODE -> "Invalid verification code"
            PhoneAuthError.CODE_EXPIRED -> "Code has expired. Request a new one"
            PhoneAuthError.NETWORK_ERROR -> "Network error. Check your connection"
            else -> "Verification failed"
        }
        VolleyLog.d(tag, "handleVerifyCodeError() showing message: $message")
        uiStateMutable.update {
            it.copy(
                isLoading = false,
                isCodeInputError = true
            )
        }
        uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError(message))
    }

    private fun normalizePhone(phone: String): String {
        return if (phone.startsWith("+")) {
            phone
        } else {
            "+$phone"
        }
    }

    private fun startResendTimer() {
        resendTimerJob?.cancel()
        resendTimerJob = viewModelScope.launch {
            for (seconds in RESEND_TIMEOUT_SECONDS downTo 1) {
                uiStateMutable.update {
                    it.copy(remainingResendTime = seconds)
                }
                delay(timeMillis = 1000)
            }

            uiStateMutable.update {
                it.copy(
                    remainingResendTime = 0,
                    isResendEnabled = true,
                )
            }
        }
    }

    private companion object {
        const val RESEND_TIMEOUT_SECONDS = 30
        const val UNEXPECTED_ERROR = "Unexpected error"
        const val SMS_CODE_LENGTH = 6
    }
}
