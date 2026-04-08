package cy.volleybolley.core.presentation.ui.screens.games.state

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model.GameData

interface GameDataManager {
    suspend fun saveGame(gameData: GameData): VolleyResult<Int, ErrorType>
}
