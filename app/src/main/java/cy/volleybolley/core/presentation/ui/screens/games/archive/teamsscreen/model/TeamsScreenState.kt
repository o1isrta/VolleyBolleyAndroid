package cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.model

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.provideMockTeams
import cy.volleybolley.games.domain.model.entity.Team

data class TeamsScreenState(
    val teams: List<Team> = provideMockTeams(),
    val isIndividual: Boolean = false
) : UiState
