package cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.model

import cy.volleybolley.core.presentation.ui.screens.games.archive.util.provideMockTeams
import cy.volleybolley.games.domain.model.entity.Team

sealed interface TeamsScreenState {
    data class Players(val players: List<Team> = provideMockTeams()) : TeamsScreenState
    data class Teams(val teams: List<Team> = provideMockTeams()) : TeamsScreenState
}
