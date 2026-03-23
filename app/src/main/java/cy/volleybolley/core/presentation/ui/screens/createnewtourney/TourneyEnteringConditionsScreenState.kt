package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import cy.volleybolley.core.presentation.base.UiState

data class TourneyEnteringConditionsScreenState(
    val maximumPlayers: Int = 8,
    val perPerson: String = "5.0",
    val accountNumber: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isPrivate: Boolean = false
) : UiState
