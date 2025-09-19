package cy.volleybolley.core.presentation.ui.screens.authorization.onboarding

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class OnboardingViewModel :
    BaseViewModel<OnboardingState, OnboardingEvent, OnboardingEffect>(
        OnboardingState()
    ) {

    override val tag: String = "OnboardingViewModel"

    override fun obtainEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.GetStartedClicked -> {
                viewModelScope.launch {
                    sendUiEffect(OnboardingEffect.NavigateToSignUp)
                }
            }
        }
    }
}
