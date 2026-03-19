package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess.MyGamesEffect.NavigateBack
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess.MyGamesEffect.NavigateToCreateGame
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess.MyGamesEffect.NavigateToMyGame
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess.MyGamesEffect.NavigateToMyTourney
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess.MyGamesEffect.OpenMap
import java.util.Locale

class MyGamesViewModel :
    BaseViewModel<MyGamesState, MyGamesAction, MyGamesEffect>(initialState = MyGamesState()) {

    override fun obtainEvent(event: MyGamesAction) {
        when (event) {
            MyGamesAction.ClickBack -> sendUiEffect(NavigateBack)

            MyGamesAction.ClickCreateGame -> sendUiEffect(NavigateToCreateGame)

            is MyGamesAction.ClickDetails -> {
                val effect = when (event.details.gameType.uppercase(Locale.ROOT)) {
                    "GAME" -> NavigateToMyGame
                    "TOURNAMENT" -> NavigateToMyTourney
                    else -> NavigateToMyGame
                }
                sendUiEffect(effect)
            }

            is MyGamesAction.ClickMap -> sendUiEffect(OpenMap(event.location))

            MyGamesAction.Refresh -> {
                // Подтянуть из домейна список игр и hasGames
            }
        }
    }
}
