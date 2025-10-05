package cy.volleybolley.core.presentation.ui.screens.authorization.authorization

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthorizationViewModel :
    BaseViewModel<AuthorizationState, AuthorizationEvent, AuthorizationEffect>(
        AuthorizationState()
    ) {

    override val tag: String = "SignUpViewModel"

    override fun obtainEvent(event: AuthorizationEvent) {
        when (event) {
            AuthorizationEvent.ContinueWithGoogleClicked -> {
                viewModelScope.launch {
                    sendUiEffect(AuthorizationEffect.NavigateToRegistration)
                }
            }

            AuthorizationEvent.ContinueWithFacebookClicked -> {
                viewModelScope.launch {
                    sendUiEffect(AuthorizationEffect.NavigateToRegistration)
                }
            }
        }
    }
}
