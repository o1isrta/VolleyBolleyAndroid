package cy.volleybolley.profile.presentation.ui.screens.about

import cy.volleybolley.core.presentation.base.UiState

data class AboutScreenState(
    val founder: String = "",
    val designedBy: String = "",
    val developedBy: String = "",
    val isInitializedState: Boolean = false,
) : UiState
