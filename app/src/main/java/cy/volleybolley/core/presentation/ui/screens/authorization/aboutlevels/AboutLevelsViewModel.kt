package cy.volleybolley.core.presentation.ui.screens.authorization.aboutlevels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import cy.volleybolley.core.presentation.base.BaseViewModel

class AboutLevelsViewModel : BaseViewModel<AboutLevelsState, AboutLevelsEvent, AboutLevelsEffect>(AboutLevelsState()) {
    override val tag: String = "AboutLevelsViewModel"

    override fun obtainEvent(event: AboutLevelsEvent) {
        when (event) {
            AboutLevelsEvent.OnBackClicked -> viewModelScope.launch { sendUiEffect(AboutLevelsEffect.NavigateBack) }
        }
    }
}
