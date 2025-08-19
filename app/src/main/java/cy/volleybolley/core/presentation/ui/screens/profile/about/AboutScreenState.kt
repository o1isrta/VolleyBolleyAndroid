package cy.volleybolley.core.presentation.ui.screens.profile.about

import cy.volleybolley.core.presentation.base.UiState

data class AboutScreenState(
    val founder: String = "",
    val designedBy: String = "",
    val developedBy: String = "",
) : UiState
