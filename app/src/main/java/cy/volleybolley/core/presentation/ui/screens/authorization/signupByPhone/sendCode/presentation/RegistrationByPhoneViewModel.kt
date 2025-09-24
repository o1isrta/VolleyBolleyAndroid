package cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.domain.PhoneValidator
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.model.SignupByPhoneEffect
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.model.SignupByPhoneEvent
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.model.SignupByPhoneState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class RegistrationByPhoneViewModel : BaseViewModel<SignupByPhoneState, SignupByPhoneEvent, SignupByPhoneEffect>(
    initialState = SignupByPhoneState()
) {
    override val tag = RegistrationByPhoneViewModel::class.simpleName ?: ""

    override fun obtainEvent(event: SignupByPhoneEvent) {
        when (event) {
            is SignupByPhoneEvent.TypePhoneNumber -> {
                uiStateMutable.update {
                    val isPhoneValid = PhoneValidator.isValidPhoneNumber(event.text)
                    it.copy(
                        phoneNumber = event.text,
                        isBtnSendCodeEnabled = isPhoneValid,
                        isPhoneNumberInputError = event.text.isNotEmpty() && isPhoneValid.not()
                    )
                }
            }

            is SignupByPhoneEvent.SendCodeButtonClicked -> {
                launchSafe(
                    block = {
                        uiStateMutable.update { it.copy(isLoading = true) }
                        delay(timeMillis = 1000L)
                        uiEffectMutable.send(SignupByPhoneEffect.NavigateToVerifyPhoneScreen)
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
