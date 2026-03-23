package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyEffect.CancelEvent
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyEffect.InvitePlayers
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyEffect.NavigateBack
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyEffect.NavigateToChangeTeam
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyEffect.NavigateToManagePlayers
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyEffect.OpenMap
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyEffect.ShareLink

class MyTourneyViewModel :
    BaseViewModel<MyTourneyState, MyTourneyAction, MyTourneyEffect>(
        initialState = MyTourneyState()
    ) {
    override fun obtainEvent(event: MyTourneyAction) {
        when (event) {
            MyTourneyAction.ClickBack -> sendUiEffect(NavigateBack)

            is MyTourneyAction.ClickMap -> sendUiEffect(OpenMap(event.location))

            MyTourneyAction.ClickPlayersOrTeams -> {
                val isIndividual = uiState.value.details.isIndividual
                val effect = if (isIndividual) NavigateToManagePlayers else NavigateToChangeTeam
                sendUiEffect(effect)
            }

            // заглушки
            MyTourneyAction.ClickInvite -> sendUiEffect(InvitePlayers)
            MyTourneyAction.ClickShare -> sendUiEffect(ShareLink)
            MyTourneyAction.ClickCancel -> sendUiEffect(CancelEvent)

            MyTourneyAction.Refresh -> {
                // Подтянуть из домейна детали турнира и обновить _state
            }
        }
    }
}
