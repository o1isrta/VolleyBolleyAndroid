package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface MyTourneyAction : UiEvent {
    data object ClickBack : MyTourneyAction
    data class ClickMap(val location: Location) : MyTourneyAction
    data object ClickPlayersOrTeams : MyTourneyAction

    // заглушки
    data object ClickInvite : MyTourneyAction
    data object ClickShare : MyTourneyAction
    data object ClickCancel : MyTourneyAction
    data object Refresh : MyTourneyAction
}
