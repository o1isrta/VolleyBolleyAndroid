package cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.verifyCode.presentation

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.verifyCode.presentation.model.VerifyPhoneNumberEffect
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.verifyCode.presentation.model.VerifyPhoneNumberEvent
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.verifyCode.presentation.model.VerifyPhoneNumberState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class VerifyPhoneNumberViewModel :
    BaseViewModel<VerifyPhoneNumberState, VerifyPhoneNumberEvent, VerifyPhoneNumberEffect>(
        initialState = VerifyPhoneNumberState()
    ) {
    override val tag = VerifyPhoneNumberViewModel::class.simpleName ?: ""

    override fun obtainEvent(event: VerifyPhoneNumberEvent) {
        when (event) {
            is VerifyPhoneNumberEvent.TypeCode -> {
                uiStateMutable.update {
                    it.copy(
                        code = event.text,
                        isBtnVerifyEnabled = event.text.length == REQUIRED_CODE_LENGTH
                    )
                }
            }

            is VerifyPhoneNumberEvent.SendNewCodeButtonClicked -> {
                // do in future
            }

            is VerifyPhoneNumberEvent.VerifyCodeButtonClicked -> {
                launchSafe(
                    block = {
                        uiStateMutable.update { it.copy(isLoading = true) }
                        delay(timeMillis = 1000L)
                        uiEffectMutable.send(VerifyPhoneNumberEffect.NavigateToRegistrationScreen)
                        uiStateMutable.update { it.copy(isLoading = false) }
                    },
                    onError = {
                        uiStateMutable.update { it.copy(isLoading = false) }
                        // handle in future
                    },
                    getErrorLogMessage = { "error in verify code -> $it" }
                )
            }
        }
    }

    private companion object {
        const val REQUIRED_CODE_LENGTH = 6
    }
}
