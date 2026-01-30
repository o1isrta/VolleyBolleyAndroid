package cy.volleybolley.auth.phone.ui.presentation

import cy.volleybolley.auth.phone.domain.ResendCodeToken
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.auth.phone.domain.PhoneValidator
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneEffect
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneEvent
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneState
import kotlinx.coroutines.flow.update

class AuthorizationByPhoneViewModel() :
    BaseViewModel<AuthorizationByPhoneState, AuthorizationByPhoneEvent, AuthorizationByPhoneEffect>(
        initialState = AuthorizationByPhoneState()
    ) {
    override val tag = AuthorizationByPhoneViewModel::class.simpleName ?: ""

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

            AuthorizationByPhoneEvent.SendCodeClicked -> {
                sendCode()
            }

            AuthorizationByPhoneEvent.ResendCodeClicked -> {
                resendCode()
            }

            AuthorizationByPhoneEvent.VerifyCodeClicked -> {
                verifyCode()
            }
        }
    }

    private fun normalizePhone(phone: String): String {
        return if (phone.startsWith("+")) {
            phone
        } else {
            "+$phone"
        }
    }

    fun sendCode() {
        val state = uiState.value
        if (!state.isPhoneNumberValid) return

        uiStateMutable.update { it.copy(isLoading = true) }

        uiEffectMutable.trySend(
            AuthorizationByPhoneEffect.RequestSendCode(
                phone = normalizePhone(state.phoneNumber),
                resendToken = null
            )
        )
    }

    private fun verifyCode() {
        val state = uiState.value
        val verificationId = state.verificationId ?: return

        if (state.code.length < 6) {
            uiStateMutable.update { it.copy(isCodeInputError = true) }
            return
        }

        uiStateMutable.update { it.copy(isLoading = true) }

        uiEffectMutable.trySend(
            AuthorizationByPhoneEffect.RequestVerifyCode(
                verificationId = verificationId,
                code = state.code
            )
        )
    }

    private fun resendCode() {
        val state = uiState.value
        val token = state.resendToken ?: return

        uiStateMutable.update { it.copy(isLoading = true) }

        uiEffectMutable.trySend(
            AuthorizationByPhoneEffect.RequestSendCode(
                phone = state.phoneNumber,
                resendToken = token
            )
        )
    }

    fun onCodeSent(
        verificationId: String,
        resendToken: ResendCodeToken
    ) {
        uiStateMutable.update {
            it.copy(
                isLoading = false,
                verificationId = verificationId,
                resendToken = resendToken,
                step = AuthorizationByPhoneState.Step.VERIFY_CODE
            )
        }
    }

    fun onAuthorized(idToken: String) {
        uiStateMutable.update { it.copy(isLoading = false) }
        uiEffectMutable.trySend(AuthorizationByPhoneEffect.Authorized(idToken))
    }

    fun onError() {
        uiStateMutable.update {
            it.copy(
                isLoading = false,
                isCodeInputError = true
            )
        }
    }
}
