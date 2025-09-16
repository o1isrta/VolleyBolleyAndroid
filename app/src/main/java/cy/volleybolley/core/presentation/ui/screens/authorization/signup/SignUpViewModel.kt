package cy.volleybolley.core.presentation.ui.screens.authorization.signup

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import cy.volleybolley.core.presentation.base.BaseViewModel

class SignUpViewModel : BaseViewModel<SignUpState, SignUpEvent, SignUpEffect>(SignUpState()) {
    override val tag: String = "SignUpViewModel"

    override fun obtainEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.ContinueWithPhoneClicked -> viewModelScope.launch { sendUiEffect(SignUpEffect.NavigateToRegistrationByPhone) }
            SignUpEvent.ContinueWithGoogleClicked -> {}
            SignUpEvent.ContinueWithFacebookClicked -> {}
        }
    }
}
