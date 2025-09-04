package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

sealed interface MyTourneyAction {
    data object ClickBack : MyTourneyAction
    data class ClickMap(val location: Location) : MyTourneyAction
    data object ClickManagePlayers : MyTourneyAction
    data object ClickChangeTeam : MyTourneyAction

    // заглушки
    data object ClickInvite : MyTourneyAction
    data object ClickShare : MyTourneyAction
    data object ClickCancel : MyTourneyAction
    data object Refresh : MyTourneyAction
}
