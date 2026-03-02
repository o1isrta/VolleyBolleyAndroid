package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.BasicGameSetupRoute
import cy.volleybolley.core.presentation.ui.navigation.MyGameRoute
import cy.volleybolley.core.presentation.ui.navigation.MyTourneyRoute
import java.util.Locale

class MyGamesViewModel :
    BaseViewModel<MyGamesState, MyGamesAction, MyGamesEffect>(initialState = MyGamesState()) {

    override val tag: String = "MyGamesVM"

    override fun obtainEvent(event: MyGamesAction) {
        when (event) {
            MyGamesAction.ClickBack -> sendUiEffect(MyGamesEffect.NavigateBack)

            MyGamesAction.ClickCreateGame -> sendUiEffect(MyGamesEffect.Navigate(BasicGameSetupRoute))

            is MyGamesAction.ClickDetails -> {
                val route = when (event.details.gameType.uppercase(Locale.ROOT)) {
                    "GAME" -> MyGameRoute
                    "TOURNAMENT" -> MyTourneyRoute
                    else -> MyGameRoute
                }
                sendUiEffect(MyGamesEffect.Navigate(route))
            }

            is MyGamesAction.ClickMap -> sendUiEffect(MyGamesEffect.OpenMap(event.location))

            MyGamesAction.Refresh -> {
                // Подтянуть из домейна список игр и hasGames
            }
        }
    }
}
