package cy.volleybolley.core.presentation.ui.screens.profile.about

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface AboutScreenEvent : UiEvent {
    data object OnBackFromAboutClick: AboutScreenEvent
    data class OnStateInitialiseByResources(
        val founderName: String,
        val designersNames: String,
        val developersNames: String,
    ) : AboutScreenEvent
}
