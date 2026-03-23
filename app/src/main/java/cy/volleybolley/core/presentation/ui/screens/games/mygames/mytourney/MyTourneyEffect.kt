package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface MyTourneyEffect : UiEffect {
    data object NavigateBack : MyTourneyEffect
    data object NavigateToManagePlayers : MyTourneyEffect
    data object NavigateToChangeTeam : MyTourneyEffect
    data class OpenMap(val location: Location) : MyTourneyEffect

    // заглушки
    data object InvitePlayers : MyTourneyEffect
    data object ShareLink : MyTourneyEffect
    data object CancelEvent : MyTourneyEffect
}
