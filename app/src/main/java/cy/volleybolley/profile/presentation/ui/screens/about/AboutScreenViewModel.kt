package cy.volleybolley.profile.presentation.ui.screens.about

import cy.volleybolley.core.presentation.base.BaseViewModel

class AboutScreenViewModel : BaseViewModel<AboutScreenState, AboutScreenEvent, AboutScreenEffect>(
    initialState = AboutScreenState(),
) {
    override val tag: String = AboutScreenViewModel::class.simpleName ?: "AboutScreenViewModel"

    override fun obtainEvent(event: AboutScreenEvent) {
        when (event) {
            AboutScreenEvent.OnBackFromAboutClick -> sendUiEffect(AboutScreenEffect.NavigateFromAboutScreen(null))
        }
    }
}
