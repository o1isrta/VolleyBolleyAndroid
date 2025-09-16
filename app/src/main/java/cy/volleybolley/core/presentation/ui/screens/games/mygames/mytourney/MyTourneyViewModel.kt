package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.ChangeTeamRoute
import cy.volleybolley.core.presentation.ui.navigation.ManagePlayersRoute

class MyTourneyViewModel :
    BaseViewModel<MyTourneyState, MyTourneyAction, MyTourneyEffect>(
        initialState = MyTourneyState()
    ) {

    override val tag: String = "MyTourneyVM"

    override fun obtainEvent(event: MyTourneyAction) {
        when (event) {
            MyTourneyAction.ClickBack -> sendUiEffect(MyTourneyEffect.NavigateBack)

            is MyTourneyAction.ClickMap -> sendUiEffect(MyTourneyEffect.OpenMap(event.location))

            MyTourneyAction.ClickPlayersOrTeams -> {
                val isIndividual = uiState.value.details.isIndividual
                val route = if (isIndividual) ManagePlayersRoute else ChangeTeamRoute
                sendUiEffect(MyTourneyEffect.Navigate(route))
            }

            // заглушки
            MyTourneyAction.ClickInvite -> sendUiEffect(MyTourneyEffect.InvitePlayers)
            MyTourneyAction.ClickShare -> sendUiEffect(MyTourneyEffect.ShareLink)
            MyTourneyAction.ClickCancel -> sendUiEffect(MyTourneyEffect.CancelEvent)

            MyTourneyAction.Refresh -> {
                // TODO: подтянуть из домейна детали турнира и обновить _state
            }
        }
    }
}
