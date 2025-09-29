package cy.volleybolley.profile.presentation.ui.screens.about

import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update

class AboutScreenViewModel : BaseViewModel<AboutScreenState, AboutScreenEvent, AboutScreenEffect>(
    initialState = AboutScreenState(),
) {
    override val tag: String = AboutScreenViewModel::class.simpleName ?: "AboutScreenViewModel"

    override fun obtainEvent(event: AboutScreenEvent) {
        when (event) {
            AboutScreenEvent.OnBackFromAboutClick -> sendUiEffect(AboutScreenEffect.NavigateFromAboutScreen(null))

            is AboutScreenEvent.OnStateInitialiseByResources -> {
                uiStateMutable.update { currentState ->
                    currentState.copy(
                        founder = event.founderName,
                        designedBy = event.designersNames,
                        developedBy = event.developersNames,
                        isInitializedState = true
                    )
                }
            }
        }
    }
}
