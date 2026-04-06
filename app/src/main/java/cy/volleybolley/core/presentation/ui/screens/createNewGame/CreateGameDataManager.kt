package cy.volleybolley.core.presentation.ui.screens.createNewGame

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.screens.createNewGame.model.GameData

interface CreateGameDataManager {
    suspend fun saveGame(gameData: GameData): VolleyResult<Int, ErrorType>
}
