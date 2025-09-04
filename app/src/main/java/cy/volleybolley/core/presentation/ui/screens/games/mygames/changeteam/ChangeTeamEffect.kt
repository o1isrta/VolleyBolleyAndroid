package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam


sealed interface ChangeTeamEffect {
    data object NavigateBack : ChangeTeamEffect
    data object TeamSelected : ChangeTeamEffect
}
