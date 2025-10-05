package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.domain.PhoneValidator
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model.AuthorizationByPhoneEffect
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model.AuthorizationByPhoneEvent
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.model.AuthorizationByPhoneState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class AuthorizationByPhoneViewModel :
    BaseViewModel<AuthorizationByPhoneState, AuthorizationByPhoneEvent, AuthorizationByPhoneEffect>(
        initialState = AuthorizationByPhoneState()
    ) {
    override val tag = AuthorizationByPhoneViewModel::class.simpleName ?: ""

    override fun obtainEvent(event: AuthorizationByPhoneEvent) {
        when (event) {
            is AuthorizationByPhoneEvent.TypePhoneNumber -> {
                uiStateMutable.update {
                    val isPhoneValid = PhoneValidator.isValidPhoneNumber(event.text)
                    it.copy(
                        phoneNumber = event.text,
                        isBtnSendCodeEnabled = isPhoneValid,
                        isPhoneNumberInputError = event.text.isNotEmpty() && isPhoneValid.not()
                    )
                }
            }

            is AuthorizationByPhoneEvent.SendCodeButtonClicked -> {
                launchSafe(
                    block = {
                        uiStateMutable.update { it.copy(isLoading = true) }
                        delay(timeMillis = 1000L)
                        uiEffectMutable.send(AuthorizationByPhoneEffect.NavigateToVerifyPhoneScreen)
                        uiStateMutable.update { it.copy(isLoading = false) }
                    },
                    onError = {
                        uiStateMutable.update { it.copy(isLoading = false) }
                        // handle in future
                    },
                    getErrorLogMessage = { "error in send code -> $it" }
                )
            }
        }
    }
}
