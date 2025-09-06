package cy.volleybolley.core.presentation.ui.screens.profile.about

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.about.AboutScreenEffect.NavigateFromAboutScreen
import cy.volleybolley.core.presentation.ui.screens.profile.about.AboutScreenEvent.OnBackFromAboutClick
import cy.volleybolley.core.presentation.ui.screens.profile.about.AboutScreenEvent.OnStateInitialiseByResources
import kotlinx.coroutines.flow.update

class AboutScreenViewModel : BaseViewModel<AboutScreenState, AboutScreenEvent, AboutScreenEffect>(
    initialState = AboutScreenState(),
) {
    override val tag: String = TAG

    override fun obtainEvent(event: AboutScreenEvent) {
        when (event) {
            OnBackFromAboutClick -> sendUiEffect(NavigateFromAboutScreen(null))

            is OnStateInitialiseByResources -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        founder = event.founderName,
                        designedBy = adaptStringFromResources(event.designersNames),
                        developedBy = adaptStringFromResources(event.developersNames),
                        isInitializedState = true
                    )
                }
            }
        }
    }

    private fun adaptStringFromResources(resourceString: String): String = resourceString.replace(",", "\n", true)

    companion object {
        val TAG: String = AboutScreenViewModel::class.simpleName ?: "AboutScreenViewModel"
    }
}
